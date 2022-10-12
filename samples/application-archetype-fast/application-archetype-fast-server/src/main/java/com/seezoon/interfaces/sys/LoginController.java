package com.seezoon.interfaces.sys;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.application.authentication.LoginApplicationService;
import com.seezoon.application.authentication.dto.UsernamePasswordLoginCmd;
import com.seezoon.application.authentication.dto.clientobject.AuthorizationTokenCO;
import com.seezoon.ddd.dto.Response;

import lombok.RequiredArgsConstructor;

/**
 * 登录
 * 
 * @author dfenghuang
 * @date 2022/10/12 20:50
 */
@RestController
@RequestMapping("/login/")
@RequiredArgsConstructor
public class LoginController {

    private final LoginApplicationService loginApplicationService;

    @PostMapping("/username_password")
    public Response<AuthorizationTokenCO> usernamePasswordLogin(@RequestBody UsernamePasswordLoginCmd cmd) {
        return loginApplicationService.usernamePasswordLogin(cmd);
    }
}
