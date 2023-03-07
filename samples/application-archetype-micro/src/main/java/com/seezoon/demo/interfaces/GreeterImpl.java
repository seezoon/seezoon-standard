package com.seezoon.demo.interfaces;

import com.seezoon.demo.stub.Greeter;
import com.seezoon.demo.stub.HelloReq;
import com.seezoon.demo.stub.HelloResp;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 暴露服务
 */
@DubboService
public class GreeterImpl implements Greeter {

    @Override
    public HelloResp greet(HelloReq request) {
        return HelloResp.newBuilder().setMessage("hello world," + request.getName()).build();
    }
}
