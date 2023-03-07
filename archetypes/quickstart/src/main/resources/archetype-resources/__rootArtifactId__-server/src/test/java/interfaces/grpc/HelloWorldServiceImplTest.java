#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.interfaces.grpc;

import ${package}.BaseSpringApplicationTest;
import ${package}.helloworld.HelloWorldRequest;
import ${package}.helloworld.HelloWorldResponse;
import ${package}.helloworld.HelloWorldService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * dubbo tri 客户端
 *
 * @author dfenghuang
 * @date 2023/3/7 09:41
 */
@Disabled
class HelloWorldServiceImplTest extends BaseSpringApplicationTest {

    @DubboReference
    private HelloWorldService helloWorldService;

    @Test
    void say() {
        HelloWorldResponse response = helloWorldService.say(HelloWorldRequest.newBuilder().setName("zhangsan").build());
        System.out.println(response.getMessage());
    }
}