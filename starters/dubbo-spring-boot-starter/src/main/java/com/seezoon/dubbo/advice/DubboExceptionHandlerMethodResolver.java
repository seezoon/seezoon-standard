package com.seezoon.dubbo.advice;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Given an annotated {@link DubboAdvice} class and annotated methods with
 * {@link DubboExceptionHandler}, {@link DubboExceptionHandlerMethodResolver} resolves given
 * exception type and maps it to the corresponding method to be executed, when this exception is being raised.
 *
 * <p>
 * For an example how to make use of it, please have a look at {@link DubboExceptionHandler}.
 * <p>
 *
 * @author huangdengfeng
 * @see DubboAdvice
 * @see DubboExceptionHandler
 * @see DubboAdviceExceptionHandler
 */
public class DubboExceptionHandlerMethodResolver implements InitializingBean {

    private final Map<Class<? extends Throwable>, Method> mappedMethods = new HashMap<>(16);

    private final DubboAdviceDiscoverer dubboAdviceDiscoverer;

    private Class<? extends Throwable>[] annotatedExceptions;

    /**
     * Creates a new dubboExceptionHandlerMethodResolver.
     *
     * @param dubboAdviceDiscoverer The advice discoverer to use.
     */
    public DubboExceptionHandlerMethodResolver(final DubboAdviceDiscoverer dubboAdviceDiscoverer) {
        this.dubboAdviceDiscoverer = requireNonNull(dubboAdviceDiscoverer, "dubboAdviceDiscoverer");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dubboAdviceDiscoverer.getAnnotatedMethods()
                .forEach(this::extractAndMapExceptionToMethod);
    }

    private void extractAndMapExceptionToMethod(Method method) {

        DubboExceptionHandler annotation = method.getDeclaredAnnotation(DubboExceptionHandler.class);
        Assert.notNull(annotation, "@DubboExceptionHandler annotation not found.");
        annotatedExceptions = annotation.value();

        checkForPresentExceptionToMap(method);
        Set<Class<? extends Throwable>> exceptionsToMap = extractExceptions(method.getParameterTypes());
        exceptionsToMap.forEach(exceptionType -> addExceptionMapping(exceptionType, method));
    }

    private void checkForPresentExceptionToMap(Method method) {
        if (method.getParameterTypes().length == 0 && annotatedExceptions.length == 0) {
            throw new IllegalStateException(
                    String.format("@DubboExceptionHandler annotated method [%s] has no mapped exception!",
                            method.getName()));
        }
    }

    private Set<Class<? extends Throwable>> extractExceptions(Class<?>[] methodParamTypes) {

        Set<Class<? extends Throwable>> exceptionsToBeMapped = new HashSet<>();
        for (Class<? extends Throwable> annoClass : annotatedExceptions) {
            if (methodParamTypes.length > 0) {
                validateAppropriateParentException(annoClass, methodParamTypes);
            }
            exceptionsToBeMapped.add(annoClass);
        }

        addMappingInCaseAnnotationIsEmpty(methodParamTypes, exceptionsToBeMapped);
        return exceptionsToBeMapped;
    }

    private void validateAppropriateParentException(Class<? extends Throwable> annoClass, Class<?>[] methodParamTypes) {

        boolean paramTypeIsNotSuperclass =
                Arrays.stream(methodParamTypes).noneMatch(param -> param.isAssignableFrom(annoClass));
        if (paramTypeIsNotSuperclass) {
            throw new IllegalStateException(
                    String.format(
                            "no listed parameter argument [%s] is equal or superclass "
                                    + "of annotated @DubboExceptionHandler method declared exception [%s].",
                            Arrays.toString(methodParamTypes), annoClass));
        }
    }

    private void addMappingInCaseAnnotationIsEmpty(
            Class<?>[] methodParamTypes,
            Set<Class<? extends Throwable>> exceptionsToBeMapped) {

        @SuppressWarnings("unchecked")
        Function<Class<?>, Class<? extends Throwable>> convertSafely = clazz -> (Class<? extends Throwable>) clazz;

        Arrays.stream(methodParamTypes)
                .filter(param -> exceptionsToBeMapped.isEmpty())
                .filter(Throwable.class::isAssignableFrom)
                .map(convertSafely) // safe to call, since check for Throwable superclass
                .forEach(exceptionsToBeMapped::add);
    }

    private void addExceptionMapping(Class<? extends Throwable> exceptionType, Method method) {

        Method oldMethod = mappedMethods.put(exceptionType, method);
        if (oldMethod != null && !oldMethod.equals(method)) {
            throw new IllegalStateException("Ambiguous @DubboExceptionHandler method mapped for [" +
                    exceptionType + "]: {" + oldMethod + ", " + method + "}");
        }
    }


    /**
     * When given exception type is subtype of already provided mapped exception, this returns a valid mapped method to
     * be later executed.
     *
     * @param exceptionType exception to check
     * @param <E> type of exception
     * @return mapped method instance with its method
     */
    @NonNull
    public <E extends Throwable> Map.Entry<Object, Method> resolveMethodWithInstance(Class<E> exceptionType) {

        Method value = extractExtendedThrowable(exceptionType);
        if (value == null) {
            return new SimpleImmutableEntry<>(null, null);
        }

        Class<?> methodClass = value.getDeclaringClass();
        Object key = dubboAdviceDiscoverer.getAnnotatedBeans()
                .values()
                .stream()
                .filter(obj -> methodClass.isAssignableFrom(obj.getClass()))
                .findFirst()
                .orElse(null);
        return new SimpleImmutableEntry<>(key, value);
    }

    /**
     * Lookup if a method is mapped to given exception.
     *
     * @param exception exception to check
     * @param <E> type of exception
     * @return true if mapped to valid method
     */
    public <E extends Throwable> boolean isMethodMappedForException(Class<E> exception) {
        return extractExtendedThrowable(exception) != null;
    }

    @Nullable
    private <E extends Throwable> Method extractExtendedThrowable(Class<E> exceptionType) {

        return mappedMethods.keySet()
                .stream()
                .filter(ex -> ex.isAssignableFrom(exceptionType))
                .min(new ExceptionDepthComparator(exceptionType))
                .map(mappedMethods::get)
                .orElse(null);
    }

}
