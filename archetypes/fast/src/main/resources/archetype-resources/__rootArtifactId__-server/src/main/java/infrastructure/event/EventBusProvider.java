#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.event;

import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventBusProvider implements InitializingBean {

    private static final String EVENT_BUS_NAME = "async-event-bus-thread";
    private static final EventBus INSTANCE = new AsyncEventBus(EVENT_BUS_NAME, ForkJoinPool.commonPool());

    public static <T> void publish(T event) {
        INSTANCE.post(event);
    }

    public static EventBus getInstance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE.register(this);
    }
}
