package com.seezoon.ddd.autoconfigure;

import java.util.Optional;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seezoon.ddd.advisor.ApplicationServiceAdvisor;
import com.seezoon.ddd.context.SpringContextHolder;

import lombok.extern.slf4j.Slf4j;

@Configuration(proxyBeanMethods = false)
@Slf4j
@Import(SpringContextHolder.class)
public class DDDAutoConfiguration {

    /**
     * 切面bean,如果不引入spring-boot-starter-aop 的话，默认是InfrastructureAdvisorAutoProxyCreator
     * ，需要@Role(BeanDefinition.ROLE_INFRASTRUCTURE) 才可以
     * {@link AbstractAdvisorAutoProxyCreator#getAdvicesAndAdvisorsForBean(Class, String, TargetSource)}
     *
     * @param optionalObjectMapper
     * @return
     */
    @Bean
    // @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApplicationServiceAdvisor applicationServiceAdvisor(Optional<ObjectMapper> optionalObjectMapper) {
        return new ApplicationServiceAdvisor(optionalObjectMapper.orElse(new ObjectMapper()));
    }
}
