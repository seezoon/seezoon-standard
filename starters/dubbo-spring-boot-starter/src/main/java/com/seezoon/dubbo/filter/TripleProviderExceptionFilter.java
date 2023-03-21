package com.seezoon.dubbo.filter;

import com.seezoon.dubbo.constant.Constants;
import io.envoyproxy.pgv.ValidationException;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.StatusRpcException;
import org.apache.dubbo.rpc.TriRpcStatus;

/**
 * 处理triple 异常
 * <p>
 * 因为triple是使用grpc作为底层协议的，错误码不能扩展
 * </p>
 *
 * @author dfenghuang
 * @date 2023/3/21 00:02
 */
@Activate(group = {CommonConstants.PROVIDER})
public class TripleProviderExceptionFilter implements Filter, Filter.Listener {

    /**
     * 通信成功，错误在header中体现
     */
    private StatusRpcException ok = new StatusRpcException(TriRpcStatus.OK);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (!appResponse.hasException()) {
            return;
        }
        Throwable exception = appResponse.getException();
        if (exception instanceof RpcException) {
            RpcException rpcException = (RpcException) exception;
            appResponse.setAttachment(Constants.ERROR_CODE_KEY, rpcException.getCode());
            appResponse.setAttachment(Constants.ERROR_MSG_KEY, rpcException.getMessage());
        } else if (exception instanceof ValidationException) {
            appResponse.setAttachment(Constants.ERROR_CODE_KEY, RpcException.VALIDATION_EXCEPTION);
            appResponse.setAttachment(Constants.ERROR_MSG_KEY, exception.getMessage());
        } else {
            appResponse.setAttachment(Constants.ERROR_CODE_KEY, RpcException.UNKNOWN_EXCEPTION);
            appResponse.setAttachment(Constants.ERROR_MSG_KEY, exception.getMessage());
        }
        appResponse.setException(ok);
    }

    @Override

    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
    }
}
