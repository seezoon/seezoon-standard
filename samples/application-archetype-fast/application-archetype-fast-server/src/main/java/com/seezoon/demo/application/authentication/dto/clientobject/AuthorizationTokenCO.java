package com.seezoon.demo.application.authentication.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(title = "登录凭证，添加header Authorization:Bearer token")
    private final String token;
}
