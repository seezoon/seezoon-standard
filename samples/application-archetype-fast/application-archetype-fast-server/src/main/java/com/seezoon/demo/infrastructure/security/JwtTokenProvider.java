package com.seezoon.demo.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seezoon.ddd.exception.Assertion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * token 派发与验证
 *
 * @author dfenghuang
 * @date 2022/10/11 19:19
 */
@Slf4j
public class JwtTokenProvider {

    private static final String CLAIM_CUSTOM_KEY = "custom";
    private final SecretKey key;
    private final ObjectMapper objectMapper;

    public JwtTokenProvider(String secretKey, ObjectMapper objectMapper) {
        Assertion.isTrue(StringUtils.isNotEmpty(secretKey));
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.objectMapper = objectMapper;
    }

    /**
     * 生成token ,如果需要放其他扩展body信息，使用{@link JwtBuilder#setClaims(Map)},如果需要放入header
     * 信息比如版本号{@link JwtBuilder#setHeader(Map)}
     *
     * @param jwtInfo
     * @param expireSeconds
     * @return
     */
    public String generateToken(JwtInfo jwtInfo, long expireSeconds) {
        Assertion.isTrue(null != jwtInfo);
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put(CLAIM_CUSTOM_KEY, jwtInfo);
        return Jwts.builder().serializeToJsonWith(new JacksonSerializer<>(objectMapper)).addClaims(userClaims)
            .setExpiration(DateUtils.addSeconds(new Date(), (int)expireSeconds)).signWith(key).compact();
    }

    /**
     * 提取subject
     *
     * @param token
     * @return 返回JwtInfo 为空代表token 当前不合法了
     */
    public JwtInfo getInfo(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        if (null == claimsJws || null == claimsJws.getBody()) {
            return null;
        }
        return claimsJws.getBody().get(CLAIM_CUSTOM_KEY, JwtInfo.class);
    }

    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return null != claimsJws;
    }

    private Jws<Claims> parseToken(String token) {
        Assertion.isTrue(StringUtils.isNotEmpty(token));
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                // https://github.com/jwtk/jjwt#json-jackson-custom-types
                .deserializeJsonWith(new JacksonDeserializer(Maps.of(CLAIM_CUSTOM_KEY, JwtInfo.class).build()))
                .setSigningKey(key).build().parseClaimsJws(token);
            return claimsJws;
        } catch (Exception exception) { // 如果过期了还想做逻辑，可以抓ExpiredJwtException e
            log.error("validate jwt token, error:{}", exception.getMessage());
            return null;
        }
    }

}
