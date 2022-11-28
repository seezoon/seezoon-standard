#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.repository.po;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.seezoon.mybatis.repository.po.BasePO;

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
public class SysUserPO extends BasePO<Integer> {

     /**
      * 用户编号
      */
    @NotNull
    private Integer userId;

     /**
      * 登录名
      */
    @NotBlank
    @Size(max = 50)
    private String username;

     /**
      * 密码
      */
    @Size(max = 100)
    private String password;

     /**
      * 姓名
      */
    @NotBlank
    @Size(max = 50)
    private String name;

     /**
      * 手机
      */
    @Size(max = 20)
    private String mobile;

     /**
      * 头像
      */
    @Size(max = 100)
    private String photo;

     /**
      * 邮件
      */
    @Size(max = 50)
    private String email;


    @Override
    public Integer getId() {
        return userId;
    }

    @Override
    public void setId(Integer userId) {
        this.setId(userId);
        this.userId = userId;
    }
}