#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.dto;

import com.seezoon.ddd.dto.PageQuery;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询
 */
@Getter
@Setter
public class UserPageQry extends PageQuery {

    /**
     * 登录名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机
     */
    private String mobile;

}
