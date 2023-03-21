package com.seezoon.demo.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

/**
 * 如需细化错误，请在该文件中维护对外错误，小项目一般不需要
 */
public enum ErrorCode implements ErrorDefinition {

    UNKOWN(10000, "系统错误：%s"),

    SQL_ERROR(10001, "数据库操作异常：%s"),

    PARAM_INVALID(10002, "参数错误：%s"),

    FILE_NOT_EXISTS(10003, "文件不存在"),

    FILE_SIZE_INVALID(10004, "文件大小不合法"),

    FILE_UPLOAD_FAILED(10005, "文件上传失败"),

    /**
     * 业务错误
     */
    SUPER_ADMIN_NOT_ALLOW_DELETE(20000, "超级管理员不能删除"),

    USER_NOT_EXISTS(20001, "用户不存在"),

    USER_NAME_EXISTS(20002, "用户名 %s 已存在"),

    USER_PASSWD_WRONG(20003, "用户名或密码错误"),

    OLD_PASSWD_WRONG(20004, "原密码错误"),

    USER_AUTHORIZATION(20005, "获取认证信息失败，请稍后重试"),

    USER_STATUS_INVALID(20006, "用户状态不正常");


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
