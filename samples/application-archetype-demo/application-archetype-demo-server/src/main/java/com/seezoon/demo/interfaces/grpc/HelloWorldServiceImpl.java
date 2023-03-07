package com.seezoon.demo.interfaces.grpc;

import com.seezoon.demo.helloworld.HelloWorldRequest;
import com.seezoon.demo.helloworld.HelloWorldResponse;
import com.seezoon.demo.helloworld.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public HelloWorldResponse say(HelloWorldRequest request) {
        log.debug("hello:{}", request.getName());
        return HelloWorldResponse.newBuilder().setMessage("hello " + request.getName()).build();
    }
}
