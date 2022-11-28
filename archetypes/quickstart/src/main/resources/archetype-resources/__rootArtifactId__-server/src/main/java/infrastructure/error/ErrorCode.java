#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

public enum ErrorCode implements ErrorDefinition {


    UNSPECIFIED("UNSPECIFIED", "unspecified error"),

    PARAM_INVALID("PARAM_INVALID", "param invalid"),

    INVOKE_GRPC_ERROR("INVOKE_GRPC_ERROR", "invoke grpc error"),

    SQL_ERROR("SQL_ERROR", "sql error"),

    /**
     * 业务错误
     */
    USER_NOT_EXISTS("USER_NOT_EXISTS", "用户不存在");

    private String code;
    private String msg;


    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
