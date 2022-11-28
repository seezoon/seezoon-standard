package com.seezoon.demo.application.sys.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改个人密码
 * 
 * @author dfenghuang
 * @date 2022/11/25 14:56
 */
@Getter
@Setter
public class ChangeMyPwdCmd {

    @NotBlank
    @Min(6)
    private String oldPassword;

    @NotBlank
    @Min(6)
    private String password;
}
