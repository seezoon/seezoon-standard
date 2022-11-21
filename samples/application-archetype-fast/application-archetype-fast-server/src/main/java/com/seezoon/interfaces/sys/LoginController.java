package com.seezoon.interfaces.sys;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.application.authentication.LoginApplicationService;
import com.seezoon.application.authentication.dto.UsernamePasswordLoginCmd;
import com.seezoon.application.authentication.dto.clientobject.AuthorizationTokenCO;
import com.seezoon.ddd.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * 登录
 * 
 * @author dfenghuang
 * @date 2022/10/12 20:50
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "登录", description = "可提供多种登录方式")
public class LoginController {

    private final LoginApplicationService loginApplicationService;

    @PostMapping("/username_password")
    @Operation(summary = "账号密码登录", description = "返回token，后续请求携带header，Authorization:Bearer token")
    public Response<AuthorizationTokenCO> usernamePasswordLogin(@RequestBody UsernamePasswordLoginCmd cmd) {
        return loginApplicationService.usernamePasswordLogin(cmd);
    }
}
