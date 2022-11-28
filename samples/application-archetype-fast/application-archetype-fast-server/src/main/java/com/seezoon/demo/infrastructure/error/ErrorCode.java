package com.seezoon.demo.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

/**
 * 如需细化错误，请在该文件中维护对外错误，小项目一般不需要
 */
public enum ErrorCode implements ErrorDefinition {

    UNKOWN("UNKOWN", "系统错误：%s"),

    SQL_ERROR("SQL_ERROR", "数据库操作异常：%s"),

    PARAM_INVALID("PARAM_INVALID", "参数错误：%s"),

    FILE_NOT_EXISTS("FILE_NOT_EXISTS", "文件不存在"),

    FILE_SIZE_INVALID("FILE_SIZE_INVALID", "文件大小不合法"),

    FILE_UPLOAD_FAILED("FILE_UPLOAD_FAILED", "文件上传失败"),

    /**
     * 业务错误
     */
    SUPER_ADMIN_NOT_ALLOW_DELETE("SUPER_ADMIN_NOT_ALLOW_DELETE", "超级管理员不能删除"),

    USER_NOT_EXISTS("USER_NOT_EXISTS", "用户不存在"),

    USER_NAME_EXISTS("USER_NAME_EXISTS", "用户名 %s 已存在"),

    USER_PASSWD_WRONG("USER_PASSWD_WRONG", "用户名或密码错误"),

    OLD_PASSWD_WRONG("OLD_PASSWD_WRONG", "原密码错误"),

    USER_AUTHORIZATION("USER_AUTHORIZATION_ERROR", "获取认证信息失败，请稍后重试"),

    USER_STATUS_INVALID("USER_STATUS_INVALID", "用户状态不正常");

    private String code;
    private String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
