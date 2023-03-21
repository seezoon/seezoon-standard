package com.seezoon.dubbo.advisor;

import com.seezoon.dubbo.advice.DubboAdviceExceptionHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;

/**
 * advisor for dubbo service exception.
 *
 * @author huangdengfeng
 */
public class DubboExceptionAdvisor implements PointcutAdvisor {

    private static final Pointcut pointcut = new DubboExceptionPointcut();
    private Advice advice;

    public DubboExceptionAdvisor(DubboAdviceExceptionHandler dubboAdviceExceptionHandler) {
        advice = new DubboExceptionAdvice(dubboAdviceExceptionHandler);
    }

    @Override

    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

}

/**
 * pointcut for class which has {@link org.apache.dubbo.config.annotation.DubboService} annotation
 */
class DubboExceptionPointcut implements Pointcut {

    private ClassFilter classFilter = new AnnotationClassFilter(DubboService.class, true);


    @Override
    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return new StaticMethodMatcher() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return true;
            }
        };
    }
}

@Slf4j
@RequiredArgsConstructor
class DubboExceptionAdvice implements MethodInterceptor {

    private final DubboAdviceExceptionHandler dubboAdviceExceptionHandler;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Object proceed = invocation.proceed();
            return proceed;
        } catch (Throwable t) {
            return dubboAdviceExceptionHandler.handleThrownException(t);
        }
    }
}
