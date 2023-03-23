package com.seezoon.demo;

import com.seezoon.mybatis.repository.mapper.BaseMapper;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.seezoon.demo.domain.**.mapper", markerInterface = BaseMapper.class)
@EnableDubbo(scanBasePackages = "com.seezoon.demo.interfaces.dubbo")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
