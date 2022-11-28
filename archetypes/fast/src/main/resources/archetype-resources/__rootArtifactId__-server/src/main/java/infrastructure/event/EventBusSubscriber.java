#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.event;

import org.springframework.beans.factory.InitializingBean;

/**
 * 订阅的人实现接口方便跟踪使用注解 @Subscribe
 * 
 * @author dfenghuang
 * @date 2022/10/13 00:12
 */
public interface EventBusSubscriber<T> extends InitializingBean {

    void subscribe(T event);

    @Override
    default void afterPropertiesSet() throws Exception {
        EventBusProvider.getInstance().register(this);
    }
}
