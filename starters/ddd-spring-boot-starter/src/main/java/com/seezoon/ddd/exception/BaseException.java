package com.seezoon.ddd.exception;

/**
 * Base Exception is the parent of all exceptions
 *
 * @author huangdengfeng
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(String msg, Throwable e) {
        super(msg, e);
    }

    public BaseException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }
}
