package com.seezoon.demo.infrastructure.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.seezoon.ddd.dto.Response;
import com.seezoon.ddd.exception.BizException;
import com.seezoon.demo.infrastructure.error.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过jwt 构建登录态
 *
 * @author dfenghuang
 * @date 2022/10/11 14:55
 */
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";
    private final Cache<Integer, UserInfoDetails> caches = CacheBuilder.newBuilder()
        // 设置容量大小 默认无限
        // .maximumSize(5000)
        // 设置超时时间
        .expireAfterWrite(2, TimeUnit.MINUTES).build();
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsLoader userDetailsLoader;
    private final ObjectMapper objectMapper;
    private final WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith(PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.substring(PREFIX.length());
        JwtInfo jwtInfo = jwtTokenProvider.getInfo(token);
        if (null == jwtInfo || null == jwtInfo.getUserId()) {
            log.warn("can not get jwt info");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            UserInfoDetails userInfoDetails = getUserDetails(jwtInfo.getUserId());
            // 检查是否有信息变更，控制token 可以失效
            boolean check = jwtInfo.check(userInfoDetails.getCheckSum());
            if (!check) {
                log.warn("get jwt info checkSum has changed,userId:{}", jwtInfo.getUserId());
                filterChain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userInfoDetails, null, userInfoDetails.getAuthorities());
            authentication.setDetails(webAuthenticationDetailsSource.buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        } catch (BizException e) {
            log.error("get userDetails biz error:{}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        } catch (Throwable e) {
            log.error("get userDetails unkown error:{}", e.getMessage());
            // 这里面的异常会被spring security 后面的fitlter 转换成401 or 403,所以有异常提前抛出,避免抖动造成用户退出
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String content = objectMapper.writeValueAsString(
                Response.error(ErrorCode.USER_AUTHORIZATION.code(), ErrorCode.USER_AUTHORIZATION.msg()));
            try (PrintWriter writer = response.getWriter()) {
                writer.write(content);
                writer.flush();
            }
            return;
        }
    }

    private UserInfoDetails getUserDetails(Integer userId) throws Throwable {
        try {
            return caches.get(userId, () -> userDetailsLoader.loadByUserId(userId));
        } catch (ExecutionException | UncheckedExecutionException e) {
            if (null != e.getCause()) {
                throw e.getCause();
            }
            throw e;
        }
    }
}
