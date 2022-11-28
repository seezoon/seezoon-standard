#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户密码
 */
@Getter
@Setter
public class ChangeUserPwdCmd {

    @NotNull
    private Integer userId;

    @NotBlank
    @Min(6)
    private String password;

}
