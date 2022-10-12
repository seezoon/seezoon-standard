package com.seezoon.application.authentication.dto.clientobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 登录凭据
 * 
 * @author dfenghuang
 * @date 2022/10/12 12:56
 */
@Getter
@Setter
@RequiredArgsConstructor
public class AuthorizationTokenCO {
    private final String token;
    private String desc = "Authorization:Bearer token";
}
