#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.valueobject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户值对象
 * 
 * @author dfenghuang
 * @date 2022/11/21 12:39
 */
@Getter
@Setter
public class ModifyUserVO {

    /**
     * 用户ID
     */
    @NotNull
    private Integer userId;
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
    /**
     * @see UserStatusVO
     */
    @NotNull
    private Integer status;
}
