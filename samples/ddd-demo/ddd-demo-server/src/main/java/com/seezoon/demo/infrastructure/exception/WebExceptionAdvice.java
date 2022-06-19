package com.seezoon.demo.infrastructure.exception;

import java.sql.SQLException;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.seezoon.ddd.dto.Response;
import com.seezoon.ddd.exception.BizException;
import com.seezoon.ddd.exception.SysException;
import com.seezoon.demo.infrastructure.error.ErrorCode;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

/**
 * web exception advice
 *
 * @author huangdengfeng
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionAdvice {

    /**
     * for Receiving parameters and verification
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, ValidationException.class,
        MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, BindException.class})
    public Response parameterInvalidException(Exception e) {
        return Response.error(ErrorCode.PARAM_INVALID.code(), e.getMessage());
    }

    /**
     * for using {@link org.springframework.util.Assert}
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public Response illegalArgumentException(IllegalArgumentException e) {
        return Response.error(ErrorCode.PARAM_INVALID.code(), e.getMessage());
    }

    @ExceptionHandler({SQLException.class, TransactionException.class})
    public Response sqlException(Exception e) {
        return Response.error(ErrorCode.SQL_ERROR.code(), e.getMessage());
    }

    /**
     * 调用下游GRPC 遇到错误时候
     *
     * @param e
     * @return
     */
    @ExceptionHandler(StatusRuntimeException.class)
    public Response statusRuntimeException(StatusRuntimeException e) {
        final String code = GrpcCustomHeader.getCode(e);
        final String msg = GrpcCustomHeader.getMsg(e);
        if (StringUtils.isNotEmpty(code)) {
            return Response.error(ErrorCode.INVOKE_GRPC_ERROR.code(), "code:" + code + ",msg:" + msg);
        }
        return Response.error(ErrorCode.INVOKE_GRPC_ERROR.code(), e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    public Response bizException(BizException e) {
        return Response.error(e.getcode(), e.getMessage());
    }

    @ExceptionHandler(SysException.class)
    public Response sysException(SysException e) {
        log.error("sys exception", e);
        return Response.error(e.getcode(), e.getMessage());
    }

    /**
     * using this if there is no match.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        log.error("unspecified exception", e);
        return Response.error(ErrorCode.UNSPECIFIED.code(), e.getMessage());
    }
}
