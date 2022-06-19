package com.seezoon.demo.interfaces.grpc;

import com.seezoon.demo.helloworld.HelloWorld.HelloWorldRequest;
import com.seezoon.demo.helloworld.HelloWorld.HelloWorldResponse;
import com.seezoon.demo.helloworld.HelloWorldServiceGrpc.HelloWorldServiceImplBase;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class HelloWorldService extends HelloWorldServiceImplBase {

    @Override
    public void say(HelloWorldRequest request, StreamObserver<HelloWorldResponse> responseObserver) {
        log.debug("hello:{}", request.getName());
        responseObserver.onNext(HelloWorldResponse.newBuilder().setMessage("hello " + request.getName()).build());
        responseObserver.onCompleted();
    }
}
