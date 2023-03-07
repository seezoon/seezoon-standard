package com.seezoon.demo.interfaces;


import com.seezoon.demo.stub.Greeter;
import com.seezoon.demo.stub.HelloReq;
import com.seezoon.demo.stub.HelloResp;
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