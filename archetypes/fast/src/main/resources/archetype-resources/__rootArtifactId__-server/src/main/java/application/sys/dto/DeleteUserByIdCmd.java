#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 删除用户
 */
@Getter
@Setter
public class DeleteUserByIdCmd {

    @NotNull
    private Integer userId;

    public DeleteUserByIdCmd(Integer userId) {
        this.userId = userId;
    }
}
