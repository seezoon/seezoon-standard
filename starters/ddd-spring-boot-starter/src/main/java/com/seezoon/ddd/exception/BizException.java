package com.seezoon.ddd.exception;

/**
 * BizException is known Exception, no need retry
 *
 * @author huangdengfeng
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = 1L;

    public static String DEFAULT_ERR_CODE = "BIZ_ERROR";

    public BizException(String msg) {
        super(DEFAULT_ERR_CODE, msg);
    }

    public BizException(String code, String msg) {
        super(code, msg);
    }

    public BizException(String msg, Throwable e) {
        super(DEFAULT_ERR_CODE, msg, e);
    }

    public BizException(String code, String msg, Throwable e) {
        super(code, msg, e);
    }

}