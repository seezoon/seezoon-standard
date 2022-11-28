package com.seezoon.demo.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.seezoon.demo.infrastructure.file.FileHandler;
import com.seezoon.demo.infrastructure.file.LocalFileHandler;
import com.seezoon.demo.infrastructure.properties.SeezoonProperties;

import lombok.RequiredArgsConstructor;

/**
 * 文件上传
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class UploadCustomConfiguration {

    @Bean
    public FileHandler fileHandler(SeezoonProperties seezoonProperties) {
        return new LocalFileHandler(seezoonProperties.getUpload());
    }
}
