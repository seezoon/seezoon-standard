package com.seezoon.maven.generator;

import com.seezoon.maven.generator.service.SystemGeneratorService;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.seezoon.maven.generator.dao")
@ComponentScan("com.seezoon.maven.generator.service")
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final SystemGeneratorService systemGeneratorService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        systemGeneratorService.generate();
    }
}
