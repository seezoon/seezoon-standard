package com.seezoon.infrastructure.properties;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
@ConfigurationProperties(prefix = SeezoonProperties.PREFIX)
public class SeezoonProperties {
    public static final String PREFIX = "seezoon";

    private AppProperties app = new AppProperties();
    private Map<String, List<Dict>> dict = Collections.emptyMap();
    private HttpProperties http = new HttpProperties();
    private AsyncProperties async = new AsyncProperties();
    private CorsProperties cors = new CorsProperties();
    private ScheduledProperties scheduled = new ScheduledProperties();
    private UploadProperties upload = new UploadProperties();

    /**
     * 业务配置
     */
    @Getter
    @Setter
    public static class AppProperties {
        /**
         * 登录安全密钥>=32位随机
         */
        private String secretKey;
        /**
         * 默认登录有效天数，eg 7天=7d，两小时=2h
         */
        private Duration loginExpire = Duration.ofDays(7);
    }

    @Getter
    @Setter
    public static class Dict {
        private int code;
        private String name;
        private boolean disabled;
    }

    /**
     * rest template 相关
     */
    @Getter
    @Setter
    public static class HttpProperties {
        // 连接超时 ms
        private int connectTimeout = 6 * 1000;
        // 获取数据超时 ms
        private int socketTimeout = 6 * 1000;
        // 获取连接超时ms
        private int connectionRequestTimeout = 10 * 1000;
        // 最大线程数
        private int maxTotal = 100;
        // 站点最大连接数
        private int maxPerRoute = maxTotal;
        // 不活跃多久检查ms
        private int validateAfterInactivity = 60 * 1000;
        // 重试次数 0 不重试，
        private int retyTimes = 0;
        // 空闲时间多久销毁ms
        private int idleTimeToDead = 60 * 1000;
        // 连接最多存活多久ms
        private int connTimeToLive = 60 * 1000;
        // 清理空闲线程
        private int idleScanTime = 5 * 1000;
        // 默认请求头
        private String userAgent =
            "seezoon-framework " + "(" + System.getProperty("os.name") + "/" + System.getProperty("os.version") + "/"
                + System.getProperty("os.arch") + ";" + System.getProperty("java.version") + ")";
    }

    /**
     * 异步处理配置
     */
    @Getter
    @Setter
    public static class AsyncProperties {
        private final int queueCapacity = 10000; // 缓冲队列数
        private String threadNamePrefix = "Async-Service-"; // 线程池名前缀
        private int corePoolSize = 1; // 核心线程数（默认线程数）
        private boolean allowCoreThreadTimeOut = true; // core size 可以根据自动缩到0
        private int maxPoolSize = 100; // 最大线程数
        private int keepAliveTime = 60; // 允许线程空闲时间（单位：默认为秒）
        private boolean waitForTasksToCompleteOnShutdown = true;
        private int awaitTerminationSeconds = 10;
    }

    /**
     * 前后端分离，跨域很常见，框架默认开启，线上为了安全可以设置allowedOrigins
     *
     * also see {@link CorsConfiguration}
     */
    @Getter
    @Setter
    public static class CorsProperties {

        private boolean enable = false;
        /**
         * 路径{@link org.springframework.util.AntPathMatcher}
         */
        private String mapping = "/**";
        // 配置时候可以逗号分隔,可以写实际域名,如https://www.seeezoon.com
        private String[] allowedOriginPatterns = {CorsConfiguration.ALL};
        private String[] allowedMethods = {CorsConfiguration.ALL};
        private String[] allowedHeaders = {CorsConfiguration.ALL};
        private boolean allowCredentials = true;
        private long maxAge = 1800;
    }

    /**
     * 定时任务线程配置
     */
    @Getter
    @Setter
    public static class ScheduledProperties {
        private int corePoolSize = 1; // 核心线程数（默认线程数）
        private int maxPoolSize = 100; // 最大线程数
        private int keepAliveTime = 60; // 允许线程空闲时间（单位：默认为秒）
    }

    /**
     * 文件上传配置
     */
    @Getter
    @Setter
    public static class UploadProperties {

        /**
         * 可访问的网址前缀，如https://xxx.com
         */
        private String urlPrefix;
        // 图片压缩开关
        private boolean enableImageCompress = false;
        // 图片输出质量 1 百分百输出 < 1
        private float imageQuality = 1;
        // 缩放1 为不缩放 < 1
        private double iamgeScale = 1;
        // 上传目录
        private String directory = "./upload";

    }
}
