package com.seezoon.dubbo.advice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils.MethodFilter;

/**
 * A discovery class to find all Beans annotated with {@link DubboAdvice} and for all found beans a second
 * search is performed looking for methods with {@link DubboExceptionHandler}.
 *
 * @author huangdengfeng
 * @see DubboAdvice
 * @see DubboExceptionHandler
 */
@Slf4j
public class DubboAdviceDiscoverer implements InitializingBean, ApplicationContextAware {

    /**
     * A filter for selecting {@code @DubboExceptionHandler} methods.
     */
    public static final MethodFilter EXCEPTION_HANDLER_METHODS =
            method -> AnnotatedElementUtils.hasAnnotation(method, DubboExceptionHandler.class);

    private ApplicationContext applicationContext;
    private Map<String, Object> annotatedBeans;
    private Set<Method> annotatedMethods;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        annotatedBeans = applicationContext.getBeansWithAnnotation(DubboAdvice.class);
        annotatedBeans.forEach(
                (key, value) -> log.debug("Found dubbo advice: " + key + ", class: " + value.getClass().getName()));

        annotatedMethods = findAnnotatedMethods();
    }

    private Set<Method> findAnnotatedMethods() {
        return this.annotatedBeans.values().stream()
                .map(Object::getClass)
                .map(this::findAnnotatedMethods)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Method> findAnnotatedMethods(final Class<?> clazz) {
        return MethodIntrospector.selectMethods(clazz, EXCEPTION_HANDLER_METHODS);
    }

    public Map<String, Object> getAnnotatedBeans() {
        Assert.state(annotatedBeans != null, "@TrpcAdvice annotation scanning failed.");
        return annotatedBeans;
    }

    public Set<Method> getAnnotatedMethods() {
        Assert.state(annotatedMethods != null, "@TrpcExceptionHandler annotation scanning failed.");
        return annotatedMethods;
    }

}
