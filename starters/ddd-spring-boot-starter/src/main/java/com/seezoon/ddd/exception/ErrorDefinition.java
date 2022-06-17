package com.seezoon.ddd.exception;

/**
 * Extends your error codes in your App by implements this Interface
 *
 * @author huangdengfeng
 */
public interface ErrorDefinition {

    String code();

    String msg();

}