package com.seezoon.ddd.advisor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seezoon.ddd.annotation.ApplicationService;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;

/**
 * advisor for application service. print param and result when log level debug enable
 *
 * @author huangdengfeng
 */
public class ApplicationServiceAdvisor extends AbstractPointcutAdvisor {

    private static final Pointcut pointcut = new ApplicationServicePointcut();
    private Advice advice;

    public ApplicationServiceAdvisor(ObjectMapper objectMapper) {
        advice = new ApplicationServiceAdvice(objectMapper);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}

/**
 * pointcut for class which has {@link ApplicationService} annotation
 */
class ApplicationServicePointcut implements Pointcut {

    public MethodMatcher methodMatcher = new StaticMethodMatcher() {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return Modifier.isPublic(method.getModifiers());
        }
    };

    private ClassFilter classFilter = new AnnotationClassFilter(ApplicationService.class);

    @Override
    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}

@Slf4j
@RequiredArgsConstructor
class ApplicationServiceAdvice implements MethodInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        this.logRequest(invocation);
        Object proceed = invocation.proceed();
        this.logResponse(startTime, proceed);
        return proceed;
    }

    private void logRequest(MethodInvocation invocation) {
        if (!log.isDebugEnabled()) {
            return;
        }
        try {
            log.debug("start processing:" + invocation.getMethod());
            Object[] args = invocation.getArguments();
            for (Object arg : args) {
                log.debug("param:" + objectMapper.writeValueAsString(arg));
            }
        } catch (Exception e) {
            //swallow it
            log.error("log reqeust error", e);
        }
    }

    private void logResponse(long startTime, Object result) {
        if (!log.isDebugEnabled()) {
            return;
        }
        try {
            long endTime = System.currentTimeMillis();
            log.debug("end process cost : " + (endTime - startTime) + "ms, result : " + objectMapper.writeValueAsString(
                    result));
        } catch (Exception e) {
            //swallow it
            log.error("log response error", e);
        }
    }
}