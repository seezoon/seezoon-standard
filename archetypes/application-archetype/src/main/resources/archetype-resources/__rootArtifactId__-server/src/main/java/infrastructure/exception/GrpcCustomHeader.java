#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.exception;

import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;

public class GrpcCustomHeader {

    public static final Metadata.Key<String> RESP_ERROR_CODE =
        Metadata.Key.of("x-error-code", Metadata.ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> RESP_ERROR_MSG =
        Metadata.Key.of("x-error-msg", Metadata.ASCII_STRING_MARSHALLER);

    public static String getCode(StatusRuntimeException e) {
        Metadata trailers = e.getTrailers();
        String code = trailers.get(GrpcCustomHeader.RESP_ERROR_CODE);
        final String msg = trailers.get(GrpcCustomHeader.RESP_ERROR_MSG);
        return code;
    }

    public static String getMsg(StatusRuntimeException e) {
        Metadata trailers = e.getTrailers();
        String msg = trailers.get(GrpcCustomHeader.RESP_ERROR_MSG);
        return msg;
    }
}
