#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.devh.boot.grpc.client.inject.StubTransformer;

@Configuration
public class GrpcConfiguration {

    @Value("${symbol_dollar}{grpc.client.read-timeout:10s}")
    private Duration readTimeout;

    @Bean
    public StubTransformer callTimeoutStubTransfomrer() {
        return (name, stub) -> stub.withDeadlineAfter(readTimeout.toMillis(), TimeUnit.MILLISECONDS);
    }
}
