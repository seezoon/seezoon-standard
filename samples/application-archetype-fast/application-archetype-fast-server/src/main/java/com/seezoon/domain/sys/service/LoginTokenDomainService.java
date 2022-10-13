package com.seezoon.domain.sys.service;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.seezoon.infrastructure.security.JwtInfo;
import com.seezoon.infrastructure.security.JwtTokenProvider;
import com.seezoon.infrastructure.security.UserInfoDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录token
 * 
 * @author dfenghuang
 * @date 2022/10/13 15:00
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class LoginTokenDomainService {

    private final UserDetailsLoaderDomainService userDetailsLoaderDomainService;
    private final JwtTokenProvider jwtTokenProvider;

    public String generateToken(@NotNull Integer userId, long expireSeconds) {
        UserInfoDetails userInfoDetails = userDetailsLoaderDomainService.loadByUserId(userId);
        String token = jwtTokenProvider.generateToken(
            new JwtInfo(userInfoDetails.getUsername(), userInfoDetails.getUserId(), userInfoDetails.getCheckSum()),
            expireSeconds);
        return token;
    }
}
