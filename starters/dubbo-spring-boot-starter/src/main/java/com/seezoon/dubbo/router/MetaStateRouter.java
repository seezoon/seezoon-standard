package com.seezoon.dubbo.router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.Holder;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.RouterSnapshotNode;
import org.apache.dubbo.rpc.cluster.router.state.AbstractStateRouter;
import org.apache.dubbo.rpc.cluster.router.state.BitList;

/**
 * 元数据路由
 * </p>
 *
 * <pre>
 * 1. 如果设置了自定义的元数据，元数据需要严格匹配；
 * 2. 如果设置了就近路由元数据，按set->idc->city的维度逐步找
 * 3. 如果关闭就近路由，就近的元数据存在的话，也需要严格匹配到
 * </pre>
 *
 * @author dfenghuang
 * @date 2023/3/27 23:42
 */
@Slf4j
public class MetaStateRouter<T> extends AbstractStateRouter<T> {

    private static final int size = 5;
    private final RouterMeta routerMeta;

    public MetaStateRouter(RouterMeta routerMeta, URL url) {
        super(url);
        this.routerMeta = routerMeta;
    }

    @Override
    protected BitList<Invoker<T>> doRoute(BitList<Invoker<T>> invokers, URL url, Invocation invocation,
            boolean needToPrintMessage, Holder<RouterSnapshotNode<T>> routerSnapshotNodeHolder,
            Holder<String> messageHolder) throws RpcException {
        if (routerMeta.isEmpty()) {
            return invokers;
        }
        if (log.isDebugEnabled()) {
            log.debug("consumer:{},router meta:{}", url, routerMeta);
        }
        int minSize = Math.min(size, invokers.size());
        List<Invoker<T>> result = new ArrayList<>(invokers.size());
        List<Invoker<T>> setResult = new ArrayList<>(minSize);
        List<Invoker<T>> idcResult = new ArrayList<>(minSize);
        List<Invoker<T>> cityResult = new ArrayList<>(minSize);
        String consumerSet = invocation.getAttachment(RouterMeta.CONSUMER_SET_KEY, routerMeta.getSet());
        String consumerIdc = invocation.getAttachment(RouterMeta.CONSUMER_IDC_KEY, routerMeta.getIdc());
        String consumerCity = invocation.getAttachment(RouterMeta.CONSUMER_CITY_KEY, routerMeta.getCity());
        for (Invoker<T> invoker : invokers) {
            Map<String, String> parameters = invoker.getUrl().getParameters();
            // 自定义的元数据必须匹配
            boolean customMetaMatched = true;
            for (Entry<String, String> entry : routerMeta.getCustomMeta().entrySet()) {
                if (!Objects.equals(entry.getValue(), parameters.get(entry.getKey()))) {
                    customMetaMatched = false;
                }
            }
            if (!customMetaMatched) {
                continue;
            }
            if (routerMeta.isNearbyMetaEmpty()) {
                continue;
            }
            String providerSet = parameters.get(RouterMeta.SET);
            String providerIdc = parameters.get(RouterMeta.IDC);
            String providerCity = parameters.get(RouterMeta.CITY);
            // 不开就近，就需要全匹配
            if (!routerMeta.isNearbyRoute()) {
                if (StringUtils.isNotEmpty(consumerSet) && !Objects.equals(consumerSet, providerSet)) {
                    continue;
                }
                if (StringUtils.isNotEmpty(consumerIdc) && !Objects.equals(consumerIdc,
                        providerIdc)) {
                    continue;
                }
                if (StringUtils.isNotEmpty(consumerCity) && !Objects.equals(consumerCity,
                        providerCity)) {
                    continue;
                }
                result.add(invoker);
                continue;
            }
            if (StringUtils.isNotEmpty(consumerSet) && Objects.equals(consumerSet,
                    providerSet)) {
                setResult.add(invoker);
                continue;
            }
            if (StringUtils.isNotEmpty(consumerIdc) && Objects.equals(consumerIdc,
                    providerIdc)) {
                idcResult.add(invoker);
                continue;
            }
            if (StringUtils.isNotEmpty(consumerCity) && Objects.equals(consumerCity,
                    providerCity)) {
                cityResult.add(invoker);
            }
        }
        // 如果不开就近
        if (!routerMeta.isNearbyRoute()) {
            return new BitList<>(result);
        }
        // 或者距离标签配置则返回全量
        if (routerMeta.isNearbyMetaEmpty()) {
            return new BitList<>(result);
        }

        // 就近路由
        if (!setResult.isEmpty()) {
            log.debug("use set nearby meta:{}", consumerSet);
            return new BitList<>(setResult);
        }
        if (!idcResult.isEmpty()) {
            log.debug("use idc nearby meta:{}", consumerIdc);
            return new BitList<>(idcResult);
        }
        if (!cityResult.isEmpty()) {
            log.debug("use city nearby meta:{}", consumerCity);
            return new BitList<>(cityResult);
        }
        log.debug("can not find any nearby provider");
        return new BitList<>(result);
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public boolean isForce() {
        return true;
    }
}
