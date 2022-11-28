#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.valueobject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * 添加用户值对象
 * 
 * @author dfenghuang
 * @date 2022/11/21 12:39
 */
@Getter
@Setter
public class AddUserVO {
    /**
     * 登录名
     */
    @NotBlank
    private String username;
    /**
     * 姓名
     */
    @NotBlank
    private String name;
    /**
     * 密码
     */
    @Size(min = 6)
    private String password;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 头像
     */
    private String photo;
    /**
     * 邮件
     */
    private String email;
}
