package com.seezoon.dubbo.advice;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

/**
 * As part of {@link DubboAdvice @TrpcAdvice}, when a thrown exception is caught during dubbo calls, then this thrown
 * exception is being handled. The
 * {@link DubboExceptionHandlerMethodResolver} provides a mapping for exceptions and their respective handler methods.
 *
 * <p>
 * The response is derived from methods annotated with {@link DubboExceptionHandler} inside {@link DubboAdvice} beans.
 * </p>
 *
 * @author huangdengfeng
 * @see DubboAdvice
 * @see DubboExceptionHandler
 * @see DubboExceptionHandlerMethodResolver
 */
@Slf4j
public class DubboAdviceExceptionHandler {

    private final DubboExceptionHandlerMethodResolver dubboExceptionHandlerMethodResolver;

    /**
     * Creates a new {@link DubboAdvice} powered {@link DubboExceptionHandler}.
     *
     * @param dubboExceptionHandlerMethodResolver The method resolver to use.
     */
    public DubboAdviceExceptionHandler(
            final DubboExceptionHandlerMethodResolver dubboExceptionHandlerMethodResolver) {
        this.dubboExceptionHandlerMethodResolver =
                requireNonNull(dubboExceptionHandlerMethodResolver, "dubboExceptionHandlerMethodResolver");
    }


    /**
     * Given an exception, a lookup is performed to retrieve mapped method. <br>
     * In case of successful returned method, and matching exception parameter type for given exception, the exception
     * is handed over to retrieved method. Retrieved method is then being invoked.
     *
     * @param exception exception to search for
     * @return result of invoked mapped method to given exception
     * @throws Throwable rethrows exception if no mapping existent or exceptions raised by implementation
     */
    @Nullable
    public Object handleThrownException(final Throwable exception) throws Throwable {
        log.debug("Exception caught during dubbo execution: {}", exception.getMessage());
        final Class<? extends Throwable> exceptionClass = exception.getClass();
        final boolean exceptionIsMapped =
                this.dubboExceptionHandlerMethodResolver.isMethodMappedForException(exceptionClass);
        if (!exceptionIsMapped) {
            throw exception;
        }

        final Entry<Object, Method> methodWithInstance =
                this.dubboExceptionHandlerMethodResolver.resolveMethodWithInstance(exceptionClass);
        final Method mappedMethod = methodWithInstance.getValue();
        final Object instanceOfMappedMethod = methodWithInstance.getKey();
        final Object[] instancedParams = determineInstancedParameters(mappedMethod, exception);

        return invokeMappedMethodSafely(mappedMethod, instanceOfMappedMethod, instancedParams);
    }

    private Object[] determineInstancedParameters(final Method mappedMethod, final Throwable exception) {

        final Parameter[] parameters = mappedMethod.getParameters();
        final Object[] instancedParams = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            final Class<?> parameterClass = convertToClass(parameters[i]);
            if (parameterClass.isAssignableFrom(exception.getClass())) {
                instancedParams[i] = exception;
                break;
            }
        }
        return instancedParams;
    }

    private Class<?> convertToClass(final Parameter parameter) {
        final Type paramType = parameter.getParameterizedType();
        if (paramType instanceof Class) {
            return (Class<?>) paramType;
        }
        throw new IllegalStateException("Parameter type of method has to be from Class, it was: " + paramType);
    }

    private Object invokeMappedMethodSafely(
            final Method mappedMethod,
            final Object instanceOfMappedMethod,
            final Object[] instancedParams) throws Throwable {
        try {
            return mappedMethod.invoke(instanceOfMappedMethod, instancedParams);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw e.getCause(); // throw the exception thrown by implementation
        }
    }
}
