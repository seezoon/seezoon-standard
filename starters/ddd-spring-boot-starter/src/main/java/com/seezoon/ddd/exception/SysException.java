package com.seezoon.ddd.exception;

/**
 * System Exception is unexpected Exception, retry might work again
 *
 * @author huangdengfeng
 */
public class SysException extends BaseException {

    private static final long serialVersionUID = 1L;

    public static String DEFAULT_ERR_CODE = "SYS_ERROR";

    public SysException(String msg) {
        super(DEFAULT_ERR_CODE, msg);
    }

    public SysException(String code, String msg) {
        super(code, msg);
    }

    public SysException(String msg, Throwable e) {
        super(DEFAULT_ERR_CODE, msg, e);
    }

    public SysException(String code, String msg, Throwable e) {
        super(code, msg, e);
    }

}
