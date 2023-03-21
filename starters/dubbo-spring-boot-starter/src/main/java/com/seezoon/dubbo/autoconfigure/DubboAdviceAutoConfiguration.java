package com.seezoon.dubbo.autoconfigure;

import com.seezoon.dubbo.advice.DubboAdviceDiscoverer;
import com.seezoon.dubbo.advice.DubboAdviceExceptionHandler;
import com.seezoon.dubbo.advice.DubboAdviceIsPresentCondition;
import com.seezoon.dubbo.advice.DubboExceptionHandlerMethodResolver;
import com.seezoon.dubbo.advisor.DubboExceptionAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@Conditional(DubboAdviceIsPresentCondition.class)
public class DubboAdviceAutoConfiguration {

    @Bean
    public DubboAdviceDiscoverer trpcAdviceDiscoverer() {
        return new DubboAdviceDiscoverer();
    }

    @Bean
    public DubboExceptionHandlerMethodResolver trpcExceptionHandlerMethodResolver(
            DubboAdviceDiscoverer dubboAdviceDiscoverer) {
        return new DubboExceptionHandlerMethodResolver(dubboAdviceDiscoverer);
    }

    @Bean
    public DubboExceptionAdvisor trpcExceptionAdvisor(
            DubboExceptionHandlerMethodResolver dubboExceptionHandlerMethodResolver) {
        DubboAdviceExceptionHandler dubboAdviceExceptionHandler = new DubboAdviceExceptionHandler(
                dubboExceptionHandlerMethodResolver);
        return new DubboExceptionAdvisor(dubboAdviceExceptionHandler);
    }

}
