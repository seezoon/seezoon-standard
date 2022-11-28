package com.seezoon.demo.application.sys.dto.clientobject;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.demo.domain.sys.valueobject.UserStatusVO;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回给调用放的数据，如果多个应用服务返回相同数据，则允许复用
 */
@Getter
@Setter
public class UserCO {

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 头像
     */
    private String photo;

    /**
     * 邮件
     */
    private String email;

    /**
     * 状态 {@link UserStatusVO}
     */
    private Integer status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
