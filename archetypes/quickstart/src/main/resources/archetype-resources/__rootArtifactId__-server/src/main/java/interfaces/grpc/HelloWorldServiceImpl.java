#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.interfaces.grpc;

import ${package}.helloworld.HelloWorldRequest;
import ${package}.helloworld.HelloWorldResponse;
import ${package}.helloworld.HelloWorldService;
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
