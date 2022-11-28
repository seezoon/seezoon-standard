#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.authentication;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ${package}.infrastructure.security.UserInfoDetails;

/**
 * 只允许应用层使用
 */
public class SecurityUtils {

    public static final Integer SUPER_ADMIN_USER_ID = 1;
    public static final Integer ANONYMOUS_USER_ID = -1;

    public static Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 匿名也是Authenticated = true
        if (null != authentication && authentication.isAuthenticated()) {
            // 如果是匿名，返回的是个字符串
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserInfoDetails) {
                UserInfoDetails userDetails = (UserInfoDetails)principal;
                return userDetails.getUserId();
            }
        }
        return null;
    }

    public static boolean isSuperAdmin(Integer userId) {
        return Objects.equals(userId, SUPER_ADMIN_USER_ID);
    }

    public static boolean isSuperAdmin() {
        return isSuperAdmin(getUserId());
    }

}