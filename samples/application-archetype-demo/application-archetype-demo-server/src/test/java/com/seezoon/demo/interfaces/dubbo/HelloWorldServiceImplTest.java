package com.seezoon.demo.interfaces.dubbo;

import com.seezoon.demo.BaseSpringApplicationTest;
import com.seezoon.demo.helloworld.HelloWorldRequest;
import com.seezoon.demo.helloworld.HelloWorldResponse;
import com.seezoon.demo.helloworld.HelloWorldService;
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