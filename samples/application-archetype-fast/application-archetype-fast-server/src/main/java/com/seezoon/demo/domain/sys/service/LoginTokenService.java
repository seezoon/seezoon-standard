package com.seezoon.demo.domain.sys.service;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.seezoon.demo.infrastructure.security.JwtInfo;
import com.seezoon.demo.infrastructure.security.JwtTokenProvider;
import com.seezoon.demo.infrastructure.security.UserInfoDetails;

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
public class LoginTokenService {

    private final UserDetailsLoaderService userDetailsLoaderService;
    private final JwtTokenProvider jwtTokenProvider;

    public String generateToken(@NotNull Integer userId, long expireSeconds) {
        UserInfoDetails userInfoDetails = userDetailsLoaderService.loadByUserId(userId);
        String token = jwtTokenProvider.generateToken(
            new JwtInfo(userInfoDetails.getUsername(), userInfoDetails.getUserId(), userInfoDetails.getCheckSum()),
            expireSeconds);
        return token;
    }
}
