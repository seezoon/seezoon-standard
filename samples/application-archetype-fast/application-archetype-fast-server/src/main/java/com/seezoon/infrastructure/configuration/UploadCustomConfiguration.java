package com.seezoon.infrastructure.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.seezoon.infrastructure.file.FileHandler;
import com.seezoon.infrastructure.file.LocalFileHandler;
import com.seezoon.infrastructure.properties.SeezoonProperties;

import lombok.RequiredArgsConstructor;

/**
 * 文件上传
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "seezoon.upload.directory")
public class UploadCustomConfiguration {

    @Bean
    public FileHandler fileHandler(SeezoonProperties seezoonProperties) {
        return new LocalFileHandler(seezoonProperties.getUpload());
    }
}
