#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ${package}.infrastructure.file.FileHandler;
import ${package}.infrastructure.file.LocalFileHandler;
import ${package}.infrastructure.properties.SeezoonProperties;

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
