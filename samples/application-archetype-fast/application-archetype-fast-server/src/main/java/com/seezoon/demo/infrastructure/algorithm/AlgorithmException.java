package com.seezoon.demo.infrastructure.algorithm;

/**
 * 自定义aes异常
 *
 * @author dfenghuang
 * @date 2022/11/2 10:33
 */
public class AlgorithmException extends RuntimeException {

    public AlgorithmException(String message) {
        super(message);
    }

    public AlgorithmException(Throwable cause) {
        super(cause);
    }

    public AlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }
}
