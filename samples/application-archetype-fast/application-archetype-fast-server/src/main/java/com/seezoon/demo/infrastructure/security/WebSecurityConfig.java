package com.seezoon.demo.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seezoon.demo.infrastructure.properties.SeezoonProperties;

import lombok.RequiredArgsConstructor;

/**
 * 认证、授权配置
 * 
 * @author dfenghuang
 * @date 2022/10/11 09:39
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    private static final String PUBLIC_ANT_PATH = "/public/**";
    private static final String LOGIN_ANT_PATH = "/login/**";
    private static final String[] STATIC_ANT_PATH =
        {"/pages/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenFilter jwtTokenFilter) throws Exception {
        // @formatter:off
        // 跨域需要
        http.cors().and()
            // 前后端分离不需要
            .csrf().disable();
        // 禁用自带的登录退出
        http.formLogin().disable();
        http.logout().disable();
        // 不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling()
            // 到认证环节的入口逻辑,默认是跳页
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            // 无效，在全局ExceptionAdvice 中处理
            .accessDeniedHandler(new AccessDeniedHandlerImpl());
        
        // 自定义登录不是很灵活， 参考UsernamePasswordAuthenticationFilter DaoAuthenticationProvider
        // http.authenticationProvider();
        
        http.authorizeRequests()
            .antMatchers(PUBLIC_ANT_PATH,LOGIN_ANT_PATH).permitAll()
            .anyRequest().authenticated();
        http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);

        
        // @formatter:on
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(STATIC_ANT_PATH);
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider(SeezoonProperties seezoonProperties, ObjectMapper objectMapper) {
        return new JwtTokenProvider(seezoonProperties.getApp().getSecretKey(), objectMapper);
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserDetailsLoader userDetailsLoader,
        ObjectMapper objectMapper) {
        return new JwtTokenFilter(jwtTokenProvider, userDetailsLoader, objectMapper);
    }
}
