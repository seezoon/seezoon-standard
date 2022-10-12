package com.seezoon.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.seezoon.infrastructure.properties.SeezoonProperties;
import com.seezoon.infrastructure.properties.SeezoonProperties.CorsProperties;

import lombok.RequiredArgsConstructor;

/**
 * mvc 相关配置，跨域，拦截器、过滤器等
 * 
 * @author hdf
 */
@Configuration
@RequiredArgsConstructor
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    private final SeezoonProperties seezoonProperties;

    /**
     *
     * 跨域很常见，默认框架参数开启，如果想更安全，allowedOriginPatterns，如https://www.seezoon.com
     *
     * 如果使用spring security 必须配置{@code http.cors()} 否则以下配置无效
     *
     * 实际spring boot 的处理类{@link org.springframework.web.cors.reactive.CorsWebFilter}
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsProperties cors = seezoonProperties.getCors();
        registry.addMapping(cors.getMapping()).allowedOriginPatterns(cors.getAllowedOriginPatterns())
            .allowedHeaders(cors.getAllowedHeaders()).allowedMethods(cors.getAllowedMethods())
            .allowCredentials(cors.isAllowCredentials()).maxAge(cors.getMaxAge());
    }

}
