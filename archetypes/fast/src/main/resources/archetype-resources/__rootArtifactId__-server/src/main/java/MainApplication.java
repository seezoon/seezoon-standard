#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ${package}.infrastructure.properties.SeezoonProperties;
import com.seezoon.mybatis.repository.mapper.BaseMapper;

@SpringBootApplication
@EnableConfigurationProperties({SeezoonProperties.class})
@MapperScan(basePackages = "${package}.domain.**.mapper", markerInterface = BaseMapper.class)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
