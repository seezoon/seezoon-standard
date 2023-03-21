package com.seezoon.dubbo.advice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with {@link DubboExceptionHandler} are being mapped to a corresponding
 * Exception, by declaring either in {@link DubboExceptionHandler#value()} as value
 * or
 * as annotated methods parameter (both is working too).
 *
 * @author huangdengfeng
 * @see DubboAdvice
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DubboExceptionHandler {

    /**
     * Exceptions handled by the annotated method.
     * <p>
     * If empty, will default to any exceptions listed in the method argument list.
     * <p>
     * <b>Note:</b> When exception types are set within value, they are prioritized in mapping the exceptions over
     * listed method arguments. And in case method arguments are provided, they <b>must</b> match the types declared
     * with this value.
     */
    Class<? extends Throwable>[] value();
}
