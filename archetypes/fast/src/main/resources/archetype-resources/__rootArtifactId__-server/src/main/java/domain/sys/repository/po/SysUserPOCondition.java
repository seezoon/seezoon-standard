#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.repository.po;

import com.seezoon.mybatis.repository.po.PagePOCondition;
import com.seezoon.mybatis.repository.sort.annotation.SortField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户信息
 * 
 * @author seezoon-generator 2022年6月16日 下午11:12:31
 */
@Getter
@Setter
@ToString
@SortField({"username:t.username", "name:t.name", "mobile:t.mobile", "createTime:t.create_time"})
public class SysUserPOCondition extends PagePOCondition {

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