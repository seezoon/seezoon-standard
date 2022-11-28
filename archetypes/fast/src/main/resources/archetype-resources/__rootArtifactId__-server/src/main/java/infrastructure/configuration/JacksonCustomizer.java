#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.configuration;

import java.io.IOException;
import java.util.TimeZone;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

/**
 * jackson configuration
 */
@Configuration
public class JacksonCustomizer {

    /**
     * json trim when deserialize
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonTrimWhenDeserialize() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.deserializerByType(String.class,
                new StdScalarDeserializer<String>(String.class) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
                        String valueAsString = jsonParser.getValueAsString();
                        if (null != valueAsString) {
                            valueAsString = StringEscapeUtils.escapeHtml4(valueAsString.trim());
                        }
                        return valueAsString;
                    }
                });
        };
    }

    /**
     * use system default timezone
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonTimeZone() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
        };
    }
}
