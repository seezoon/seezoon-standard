package com.seezoon.application.authentication.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.application.authentication.dto.UsernamePasswordLoginCmd;
import com.seezoon.application.authentication.dto.clientobject.AuthorizationTokenCO;
import com.seezoon.ddd.dto.Response;
import com.seezoon.domain.sys.repository.SysUserRepository;
import com.seezoon.domain.sys.repository.po.SysUserPO;
import com.seezoon.domain.sys.service.UserDetailsLoaderDomainService;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.properties.SeezoonProperties;
import com.seezoon.infrastructure.security.JwtInfo;
import com.seezoon.infrastructure.security.JwtTokenProvider;
import com.seezoon.infrastructure.security.PasswordEncoder;
import com.seezoon.infrastructure.security.UserInfoDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 账号密码登录
 * 
 * @author dfenghuang
 * @date 2022/10/12 12:50
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class UsernamePasswordLoginCmdExe {

    private final SysUserRepository sysUserRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SeezoonProperties seezoonProperties;
    private final UserDetailsLoaderDomainService userDetailsLoaderDomainService;

    public Response<AuthorizationTokenCO> execute(@NotNull @Valid UsernamePasswordLoginCmd cmd) {
        SysUserPO sysUserPO = sysUserRepository.findByUsername(cmd.getUsername());
        if (sysUserPO == null) {
            return Response.error(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        boolean matches = PasswordEncoder.matches(cmd.getPassword(), sysUserPO.getPassword());
        if (matches) {
            UserInfoDetails userInfoDetails = userDetailsLoaderDomainService.loadByUserId(sysUserPO.getUserId());
            String token = jwtTokenProvider.generateToken(
                new JwtInfo(sysUserPO.getUsername(), sysUserPO.getUserId(), userInfoDetails.getCheckSum()),
                seezoonProperties.getApp().getLoginExpire().getSeconds());
            return Response.success(new AuthorizationTokenCO(token));
        } else {
            return Response.error(ErrorCode.USER_PASSWD_WRONG.code(), ErrorCode.USER_PASSWD_WRONG.msg());
        }
    }
}
