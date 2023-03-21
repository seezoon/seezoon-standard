package com.seezoon.dubbo.advice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * Special {@link Component @Component} to declare global exception handling.
 *
 * <p>
 * Every class annotated with {@link DubboAdvice } is marked to be scanned for
 * {@link DubboExceptionHandler} annotations.
 * </p>
 *
 * <b>usage like:</b>
 * <pre>
 * &#64;DubboAdvice
 * public class DubboExceptionAdvice {
 *
 *     &#64;DubboExceptionHandler({IllegalArgumentException.class})
 *     public void illegalArgumentException(Exception e) {
 *         throw new RpcException(100, e.getMessage());
 *     }
 * }
 * </pre>
 *
 * @author huangdengfeng
 * @see DubboExceptionHandler
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DubboAdvice {

}
