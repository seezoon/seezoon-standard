#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.valueobject;

/**
 * 用户状态
 */
public class UserStatusVO {

    /**
     * 有效
     */
    public static final int VALID = 1;
    /**
     * 无效
     */
    public static final int INVALID = 0;

    public static boolean valid(int status) {
        return status == VALID;
    }
}
