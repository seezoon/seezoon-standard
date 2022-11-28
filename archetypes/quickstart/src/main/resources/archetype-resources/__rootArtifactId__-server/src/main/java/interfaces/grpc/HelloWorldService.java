#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.interfaces.grpc;

import ${package}.helloworld.HelloWorld.HelloWorldRequest;
import ${package}.helloworld.HelloWorld.HelloWorldResponse;
import ${package}.helloworld.HelloWorldServiceGrpc.HelloWorldServiceImplBase;

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
