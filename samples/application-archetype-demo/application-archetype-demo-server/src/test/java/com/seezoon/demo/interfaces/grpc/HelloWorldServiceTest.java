package com.seezoon.demo.interfaces.grpc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.ListenableFuture;
import com.seezoon.demo.BaseSpringApplicationTest;
import com.seezoon.demo.helloworld.HelloWorld.HelloWorldRequest;
import com.seezoon.demo.helloworld.HelloWorld.HelloWorldResponse;
import com.seezoon.demo.helloworld.HelloWorldServiceGrpc.HelloWorldServiceBlockingStub;
import com.seezoon.demo.helloworld.HelloWorldServiceGrpc.HelloWorldServiceFutureStub;
import com.seezoon.demo.helloworld.HelloWorldServiceGrpc.HelloWorldServiceStub;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Disabled
@Slf4j
class HelloWorldServiceTest extends BaseSpringApplicationTest {

    static HelloWorldRequest request = HelloWorldRequest.newBuilder().setName("grpc").build();
    @GrpcClient("helloService")
    private HelloWorldServiceStub helloWorldServiceStub;
    @GrpcClient("helloService")
    private HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;

    @GrpcClient("helloService")
    private HelloWorldServiceFutureStub helloWorldServiceFutureStub;

    @Test
    @Disabled
    void say1() {
        HelloWorldRequest request = HelloWorldRequest.newBuilder().setName("grpc").build();
        helloWorldServiceStub.say(request, new StreamObserver<>() {

            @Override
            public void onNext(HelloWorldResponse response) {
                log.debug("recieve response:{}", response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError ");
                throw new RuntimeException(t);
            }

            @Override
            public void onCompleted() {
                log.debug("onCompleted ");
            }
        });
    }

    @Test
    @Disabled
    void say2() {
        try {
            HelloWorldResponse response = helloWorldServiceBlockingStub.say(request);
            log.debug("recieve:{}", response.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void say3() throws ExecutionException, InterruptedException, TimeoutException {
        ListenableFuture<HelloWorldResponse> future = helloWorldServiceFutureStub.say(request);
        HelloWorldResponse response = future.get(6, TimeUnit.SECONDS);
        log.debug("recieve:{}", response.getMessage());
    }
}