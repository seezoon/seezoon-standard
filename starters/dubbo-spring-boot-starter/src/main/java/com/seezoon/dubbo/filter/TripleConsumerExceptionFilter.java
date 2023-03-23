package com.seezoon.dubbo.filter;

import com.seezoon.dubbo.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * 处理triple 异常
 * <p>
 * 因为triple是使用grpc作为底层协议的，错误码不能扩展
 * </p>
 *
 * @author dfenghuang
 * @date 2023/3/21 00:02
 */
@Activate(group = {CommonConstants.CONSUMER})
public class TripleConsumerExceptionFilter implements Filter, Filter.Listener {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (!appResponse.hasException()) {
            return;
        }
        // 不使用header透传异常信息，中文（需要base64）和长度限制
        String code = appResponse.getAttachment(Constants.ERROR_CODE_KEY);
        if (StringUtils.isNotEmpty(code)) {
            Throwable exception = appResponse.getException();
            appResponse.setException(new RpcException(Integer.parseInt(code), exception.getMessage()));
        }
    }

    @Override

    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }
}
