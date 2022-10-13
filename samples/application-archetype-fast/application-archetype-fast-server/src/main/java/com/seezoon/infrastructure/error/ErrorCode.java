package com.seezoon.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

public enum ErrorCode implements ErrorDefinition {

    UNSPECIFIED("UNSPECIFIED", "unspecified error"),

    PARAM_INVALID("PARAM_INVALID", "param invalid"),

    SQL_ERROR("SQL_ERROR", "sql error"),

    FILE_NOT_EXISTS("FILE_NOT_EXISTS", "文件不存在"),

    FILE_SIZE_INVALID("FILE_SIZE_INVALID", "文件大小不合法"),

    FILE_UPLOAD_FAILED("FILE_UPLOAD_FAILED", "文件上传失败"),

    /**
     * 业务错误
     */
    USER_NOT_EXISTS("USER_NOT_EXISTS", "用户不存在"),

    USER_PASSWD_WRONG("USER_PASSWD_WRONG", "用户名或密码错误"),

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
