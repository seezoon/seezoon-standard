package com.seezoon.ddd.exception;

/**
 * Assertion utility class that assists in validating arguments.
 *
 * @author huangdengfeng
 */
public abstract class Assertion {

    public static void isTrue(boolean expression, String code, String msg) {
        if (!expression) {
            throw new BizException(code, msg);
        }
    }

    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new BizException(msg);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] Must be true");
    }

    public static void notNull(Object object, String code, String msg) {
        if (object == null) {
            throw new BizException(code, msg);
        }
    }

    public static void notNull(Object object, String msg) {
        if (object == null) {
            throw new BizException(msg);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] Must not null");
    }
}
