package com.seezoon.demo.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

public enum ErrorCode implements ErrorDefinition {

    /**
     * 通用错误
     */
    UNSPECIFIED(10000, "unspecified error"),

    PARAM_INVALID(10001, "param invalid"),

    SQL_ERROR(10002, "sql error"),

    /**
     * 业务错误
     */
    USER_NOT_EXISTS(20000, "用户不存在");

    private int code;
    private String msg;


    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
