package com.seezoon.ddd.exception;

/**
 * Extends your error codes in your App by implements this Interface
 *
 * @author huangdengfeng
 */
public interface ErrorDefinition {

    /**
     * Do a good job of error code planning
     *
     * @return
     */
    int code();

    String msg();

    /**
     * 通常前缀为cmdb 规划的模块
     *
     * @return
     */
    default int prefix() {
        return 0;
    }

}