package com.seezoon.application.authentication.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * 账号密码登录
 * 
 * @author dfenghuang
 * @date 2022/10/12 12:49
 */
@Getter
@Setter
public class UsernamePasswordLoginCmd {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
