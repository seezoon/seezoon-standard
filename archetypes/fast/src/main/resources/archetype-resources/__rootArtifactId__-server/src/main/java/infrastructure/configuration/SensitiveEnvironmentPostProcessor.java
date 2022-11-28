#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.configuration;

import ${package}.infrastructure.algorithm.aes.AesCipher;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * 敏感信息处理，配置格式:ENC(base64)
 *
 * @author dfenghuang
 * @date 2022/11/3 15:03
 */
public class SensitiveEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "sensitiveProperties";
    private static final String SENSITIVE_KEY = "zPaX0pnv~nlzDu_^";
    private static final String PREFIX = "ENC(";
    private static final String SUFFIX = ")";

    private static AesCipher AES_CIPHER =
        new AesCipher(SENSITIVE_KEY.getBytes(StandardCharsets.UTF_8), SENSITIVE_KEY.getBytes(StandardCharsets.UTF_8));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter text:");
        String plain = scanner.nextLine();
        System.out.println("plain:" + plain);
        System.out.println(
            "cipher:" + Base64.encodeBase64URLSafeString(AES_CIPHER.encrypt(plain.getBytes(StandardCharsets.UTF_8))));
        scanner.close();
    }

    /**
     * 解密格式ENC(Base64)
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        if (StringUtils.isNotEmpty(content) && content.startsWith(PREFIX) && content.endsWith(SUFFIX)) {
            String encValue = content.substring(PREFIX.length(), content.length() - 1);
            String plainValue = new String(AES_CIPHER.decrypt(Base64.decodeBase64(encValue)), StandardCharsets.UTF_8);
            return plainValue;
        }
        return content;
    }

    private boolean patternMatch(String content) {
        return StringUtils.isNotEmpty(content) && content.startsWith(PREFIX) && content.endsWith(SUFFIX);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> sensitiveMap = new HashMap<>();
        MutablePropertySources propertySources = environment.getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.stream().iterator();
        while (iterator.hasNext()) {
            PropertySource<?> next = iterator.next();
            Object source = next.getSource();
            if (!(source instanceof Map)) {
                continue;
            }
            Map<String, Object> properties = (Map<String, Object>)source;
            for (Entry<String, Object> entry : properties.entrySet()) {
                if (null == entry.getValue() || !(entry.getValue() instanceof CharSequence)) {
                    continue;
                }
                String value = entry.getValue().toString();
                if (patternMatch(value)) {
                    sensitiveMap.put(entry.getKey(), decrypt(value));
                }
            }
        }
        propertySources.addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, sensitiveMap));
    }

}
