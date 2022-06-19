package com.seezoon.demo.infrastructure.exception;

import java.sql.SQLException;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.seezoon.ddd.exception.BizException;
import com.seezoon.ddd.exception.SysException;
import com.seezoon.demo.infrastructure.error.ErrorCode;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

/**
 * grpc exception advice
 *
 * @author huangdengfeng
 */
@GrpcAdvice
@Slf4j
public class GrpcExceptionAdvice {

    /**
     * for Receiving parameters and verification
     *
     * @param e
     * @return
     */
    @GrpcExceptionHandler({MissingServletRequestParameterException.class, ValidationException.class,
        MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, BindException.class})
    public StatusRuntimeException parameterInvalidException(Exception e) {
        return createByException(ErrorCode.PARAM_INVALID.code(), e.getMessage(), e);
    }

    /**
     * for using {@link org.springframework.util.Assert}
     *
     * @param e
     * @return
     */
    @GrpcExceptionHandler({IllegalArgumentException.class})
    public StatusRuntimeException illegalArgumentException(IllegalArgumentException e) {
        return createByException(ErrorCode.PARAM_INVALID.code(), e.getMessage(), e);
    }

    @GrpcExceptionHandler({SQLException.class, TransactionException.class})
    public StatusRuntimeException sqlException(Exception e) {
        return createByException(ErrorCode.SQL_ERROR.code(), e.getMessage(), e);
    }

    /**
     * 调用下游GRPC 遇到错误时候
     * 
     * @param e
     * @return
     */
    @GrpcExceptionHandler(StatusRuntimeException.class)
    public StatusRuntimeException statusRuntimeException(StatusRuntimeException e) {
        final String code = GrpcCustomHeader.getCode(e);
        final String msg = GrpcCustomHeader.getMsg(e);
        if (StringUtils.isNotEmpty(code)) {
            return createByException(ErrorCode.INVOKE_GRPC_ERROR.code(), "code:" + code + ",msg:" + msg, e);
        }
        return createByException(ErrorCode.INVOKE_GRPC_ERROR.code(), e.getMessage(), e);
    }

    @GrpcExceptionHandler(BizException.class)
    public StatusRuntimeException bizException(BizException e) {
        return createByException(e.getcode(), e.getMessage(), e);
    }

    @GrpcExceptionHandler(SysException.class)
    public StatusRuntimeException sysException(SysException e) {
        log.error("sys exception", e);
        return createByException(e.getcode(), e.getMessage(), e);
    }

    /**
     * using this if there is no match.
     *
     * @param e
     * @return
     */
    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException exception(Exception e) {
        log.error("unspecified exception", e);
        return createByException(ErrorCode.UNSPECIFIED.code(), e.getMessage(), e);
    }

    private StatusRuntimeException createByException(String code, String msg, Exception e) {
        Metadata responseHeaders = new Metadata();
        responseHeaders.put(GrpcCustomHeader.RESP_ERROR_CODE, code);
        responseHeaders.put(GrpcCustomHeader.RESP_ERROR_MSG, msg);
        return Status.INTERNAL.withCause(e).withDescription(e.getMessage()).asRuntimeException(responseHeaders);
    }

}
