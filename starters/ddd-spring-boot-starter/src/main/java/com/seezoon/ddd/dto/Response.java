package com.seezoon.ddd.dto;

/**
 * Response to caller，Can only appear in the application|interface layer
 */
public class Response<T> {

    private static final String DEFAULT_SUCCESS_CODE = "0";
    private static final String DEFAULT_SUCCESS_MSG = "success";
    private static final Response SUCCESS = new Response(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG);
    private String code;
    private String msg;
    private T data;

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Response success() {
        return SUCCESS;
    }

    public static <T> Response success(T data) {
        Response response = new Response(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG);
        response.setData(data);
        return response;
    }

    public static Response error(String code, String msg) {
        Response response = new Response(code, msg);
        return response;
    }

    public boolean isSuccess() {
        return DEFAULT_SUCCESS_CODE == code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
