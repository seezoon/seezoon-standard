package com.seezoon.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.seezoon.demo.infrastructure.properties.SeezoonProperties;
import com.seezoon.mybatis.repository.mapper.BaseMapper;

@SpringBootApplication
@EnableConfigurationProperties({SeezoonProperties.class})
@MapperScan(basePackages = "com.seezoon.demo.domain.**.mapper", markerInterface = BaseMapper.class)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
