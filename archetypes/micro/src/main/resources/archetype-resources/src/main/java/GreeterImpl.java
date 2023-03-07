#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${groupId}.demo.stub.Greeter;
import ${groupId}.demo.stub.HelloReq;
import ${groupId}.demo.stub.HelloResp;
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
