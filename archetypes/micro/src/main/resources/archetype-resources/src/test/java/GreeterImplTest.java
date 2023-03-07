#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};


import ${groupId}.demo.stub.Greeter;
import ${groupId}.demo.stub.HelloReq;
import ${groupId}.demo.stub.HelloResp;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dfenghuang
 * @date 2023/3/7 10:01
 */
@SpringBootTest
@Disabled
class GreeterImplTest {

    @DubboReference
    private Greeter greeter;

    @Test
    void greet() {
        HelloResp resp = greeter.greet(HelloReq.newBuilder().setName("zhangsan").build());
        System.out.println(resp.getMessage());
    }
}