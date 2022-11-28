package com.seezoon.demo.infrastructure.configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.seezoon.demo.infrastructure.properties.SeezoonProperties;
import com.seezoon.demo.infrastructure.properties.SeezoonProperties.ScheduledProperties;

import lombok.RequiredArgsConstructor;

/**
 * 定时配置可选
 * 
 * @see <a>https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-task-execution-sch</a>
 *      如需要使用异步，springboot 要求显示的用{@code @EnableScheduling}
 */
@Configuration
@RequiredArgsConstructor
public class ScheduledCustomConfiguration implements SchedulingConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledCustomConfiguration.class);

    private final SeezoonProperties seezoonProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ScheduledProperties scheduled = seezoonProperties.getScheduled();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            new ScheduledThreadPoolExecutor(scheduled.getCorePoolSize());
        scheduledThreadPoolExecutor.setMaximumPoolSize(scheduled.getMaxPoolSize());
        scheduledThreadPoolExecutor.setKeepAliveTime(scheduled.getKeepAliveTime(), TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.setRejectedExecutionHandler((r, executor) -> {
            logger.error("Scheduled exception:scheduled rejected");
        });
        taskRegistrar.setScheduler(scheduledThreadPoolExecutor);
    }
}
