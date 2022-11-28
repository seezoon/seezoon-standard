package com.seezoon.demo.infrastructure.security;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author dfenghuang
 * @date 2022/10/12 00:01
 */
class JwtTokenProviderTest {

    @Test
    void generateToken() {
        JwtTokenProvider jwtTokenProvider =
            new JwtTokenProvider("12345678123456781234567812345678", new ObjectMapper());
        String token = jwtTokenProvider.generateToken(new JwtInfo("u", 1, "c"), Duration.ofHours(2).getSeconds());
        System.out.println(token);
        JwtInfo info = jwtTokenProvider.getInfo(token);
        Assertions.assertEquals(info.getUserName(), "u");
    }

    @Test
    void getSubject() {}
}