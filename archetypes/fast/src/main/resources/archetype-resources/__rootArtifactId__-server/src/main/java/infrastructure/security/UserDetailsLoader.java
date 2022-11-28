#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.security;

/**
 * @author dfenghuang
 * @date 2022/10/12 22:25
 */
public interface UserDetailsLoader {
    UserInfoDetails loadByUserId(Integer userId);
}
