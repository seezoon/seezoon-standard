package com.seezoon.demo.application.sys.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.seezoon.demo.domain.sys.valueobject.UserStatusVO;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户入参
 */
@Getter
@Setter
public class ModifyUserCmd {

    @NotNull
    private Integer userId;
    /**
     * 登录名
     */
    @NotBlank
    @Size(max = 50)
    private String username;
    /**
     * 姓名
     */
    @NotBlank
    @Size(max = 50)
    private String name;
    /**
     * 手机
     */
    @Size(max = 20)
    private String mobile;
    /**
     * 头像
     */
    @Size(max = 100)
    private String photo;
    /**
     * 邮件
     */
    @Size(max = 50)
    private String email;
    /**
     * 状态 {@link UserStatusVO}
     */
    @NotNull
    private Integer status;
}
