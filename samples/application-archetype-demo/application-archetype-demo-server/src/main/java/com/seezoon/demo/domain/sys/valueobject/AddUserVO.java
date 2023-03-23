package com.seezoon.demo.domain.sys.valueobject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加用户
 */
@Getter
@Setter
public class AddUserVO {

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
}
