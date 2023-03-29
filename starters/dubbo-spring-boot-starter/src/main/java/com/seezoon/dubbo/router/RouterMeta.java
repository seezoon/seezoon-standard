package com.seezoon.dubbo.router;

import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 路由元素，消费者有router前缀，提供者不需要
 *
 * </p>
 * dubbo parameters 可以放在application,provider,consumer,reference接口或者方法，越是粒度小优先级越高
 * <b>provider使用例子</b>
 * <pre>
 * dubbo:
 *   provider:
 *     parameters:
 *       # 服务提供者元数据，消费者根据这个路由
 *       meta.set: set1
 *       meta.idc: idc1
 *       meta.city: city1
 *       meta.tenant: tenant1
 * </pre>
 * <b>consumer使用例子</b>
 * <pre>
 * dubbo:
 *   consumer:
 *     # ms
 *     timeout: 2000
 *     retries: 0
 *   reference:
 *     com.seezoon.protocol.user.server.domain.UserService:
 *       parameters:
 *         # 路由元数据
 *         router.meta.set: set1
 *         router.meta.idc: idc1
 *         router.meta.city: city1
 *         # 默认是true
 *         router.meta.nearbyRoute: false
 *         router.meta.tenant: tenant1
 * </pre>
 *
 * @author dfenghuang
 * @date 2023/3/28 22:57
 */
@Setter
@Getter
@ToString
public class RouterMeta {

    public static final String ROUTER_PREFIX = "router.";

    public static final String PREFIX = "router.meta.";

    /**
     * provider 的路由参数名
     */
    public static final String SET = "meta.set";
    public static final String IDC = "meta.idc";
    public static final String CITY = "meta.city";

    /**
     * consumer 的路由参数名
     */
    public static final String CONSUMER_SET_KEY = ROUTER_PREFIX + SET;
    public static final String CONSUMER_IDC_KEY = ROUTER_PREFIX + IDC;
    public static final String CONSUMER_CITY_KEY = ROUTER_PREFIX + CITY;
    public static final String NEARBY_ROUTE_KEY = PREFIX + "nearbyRoute";

    private String set;
    private String idc;
    private String city;
    private boolean nearbyRoute = true;
    private Map<String, String> customMeta = Collections.emptyMap();

    // 元数据为空，便于快速返回
    private boolean empty;
    // 就近路由参数为空
    private boolean nearbyMetaEmpty;
}
