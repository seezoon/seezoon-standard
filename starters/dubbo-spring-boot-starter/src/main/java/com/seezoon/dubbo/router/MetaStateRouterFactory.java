package com.seezoon.dubbo.router;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.cluster.router.state.CacheableStateRouterFactory;
import org.apache.dubbo.rpc.cluster.router.state.StateRouter;

/**
 * 元数据路由
 *
 * @author dfenghuang
 * @date 2023/3/27 23:40
 */
@Activate
public class MetaStateRouterFactory extends CacheableStateRouterFactory {

    @Override
    protected <T> StateRouter<T> createRouter(Class<T> interfaceClass, URL url) {
        Map<String, String> routerMetas = url.getParameters(v -> v.startsWith("router.meta"));
        RouterMeta routerMeta = new RouterMeta();
        Map<String, String> customMeta = new HashMap<>(routerMetas.size());
        routerMetas.forEach((k, v) -> {
            if (RouterMeta.CONSUMER_SET_KEY.equals(k)) {
                routerMeta.setSet(v);
            } else if (RouterMeta.CONSUMER_IDC_KEY.equals(k)) {
                routerMeta.setIdc(v);
            } else if (RouterMeta.CONSUMER_CITY_KEY.equals(k)) {
                routerMeta.setCity(v);
            } else if (RouterMeta.NEARBY_ROUTE_KEY.equals(k)) {
                routerMeta.setNearbyRoute(StringUtils.isNotEmpty(v) ? Boolean.parseBoolean(v) : true);
            } else {
                customMeta.put(k.substring(RouterMeta.ROUTER_PREFIX.length()), v);
            }
        });
        routerMeta.setCustomMeta(customMeta);
        routerMeta.setNearbyMetaEmpty(StringUtils.isEmpty(routerMeta.getSet())
                && StringUtils.isEmpty(routerMeta.getIdc())
                && StringUtils.isEmpty(routerMeta.getCity()));
        routerMeta.setEmpty(routerMeta.isNearbyMetaEmpty() && customMeta.isEmpty());
        return new MetaStateRouter(routerMeta, url);
    }
}
