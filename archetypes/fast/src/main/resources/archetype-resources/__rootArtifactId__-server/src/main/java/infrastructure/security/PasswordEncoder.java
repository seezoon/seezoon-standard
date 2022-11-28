#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码处理
 * 
 * @author hdf
 */
public class PasswordEncoder {

    public static String encode(String password) {
        if (StringUtils.isEmpty(password)) {
            return null;
        } else {
            return getEncoder().encode(password);
        }
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return getEncoder().matches(rawPassword, encodedPassword);
    }

    public static org.springframework.security.crypto.password.PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
