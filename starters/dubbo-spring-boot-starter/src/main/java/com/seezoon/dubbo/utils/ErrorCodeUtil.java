package com.seezoon.dubbo.utils;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.dubbo.rpc.RpcException;

/**
 * 判断错误是否框架
 *
 * @author dfenghuang
 * @date 2023/3/25 23:50
 */
public class ErrorCodeUtil {

    private static final List<Integer> errorCodes = Lists.newArrayList(
            RpcException.UNKNOWN_EXCEPTION,
            RpcException.NETWORK_EXCEPTION,
            RpcException.TIMEOUT_EXCEPTION,
            RpcException.BIZ_EXCEPTION,
            RpcException.FORBIDDEN_EXCEPTION,
            RpcException.SERIALIZATION_EXCEPTION,
            RpcException.NO_INVOKER_AVAILABLE_AFTER_FILTER,
            RpcException.LIMIT_EXCEEDED_EXCEPTION,
            RpcException.TIMEOUT_TERMINATE,
            RpcException.REGISTRY_EXCEPTION,
            RpcException.ROUTER_CACHE_NOT_BUILD,
            RpcException.METHOD_NOT_FOUND,
            RpcException.VALIDATION_EXCEPTION
    );

    /**
     * 框架内部错误码
     *
     * @param code
     * @return
     */
    public static boolean isFrame(int code) {
        return errorCodes.contains(code);
    }
}
