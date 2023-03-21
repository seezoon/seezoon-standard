package com.seezoon.dubbo.advice;

import static java.util.Objects.requireNonNull;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition to check if {@link DubboAdvice } is present. Mainly checking if {@link DubboAdviceDiscoverer}
 * should be a instantiated.
 *
 * @author huangdengfeng
 * @see DubboAdviceDiscoverer
 */
public class DubboAdviceIsPresentCondition implements ConfigurationCondition {

    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }

    @Override
    public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        final ConfigurableListableBeanFactory safeBeanFactory =
                requireNonNull(context.getBeanFactory(), "ConfigurableListableBeanFactory is null");
        return safeBeanFactory.getBeanNamesForAnnotation(DubboAdvice.class).length != 0;
    }

}
