package com.seezoon.demo.application.sys.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户手机号入参
 */
@Getter
@Setter
public class ModifyUserMobileCmd {

    /**
     * 用户id
     */
    @NotNull
    private Integer userId;

    /**
     * 手机
     */
    @NotNull
    @Size(max = 20)
    private String mobile;
    /**
     * 验证码
     */
    @NotBlank
    @Size(min = 6, max = 6)
    private String verifyCode;

}
