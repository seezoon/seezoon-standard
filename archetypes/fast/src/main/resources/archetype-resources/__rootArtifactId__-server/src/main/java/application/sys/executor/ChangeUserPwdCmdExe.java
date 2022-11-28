#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.dto.ChangeUserPwdCmd;
import ${package}.domain.sys.service.ChangePwdService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 修改用户密码
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class ChangeUserPwdCmdExe {

    private final ChangePwdService changePwdService;

    /**
     * 入口函数
     *
     * @param cmd
     */
    public Response execute(@NotNull @Valid ChangeUserPwdCmd cmd) {
        this.changePwdService.changePwd(cmd.getUserId(), cmd.getPassword());
        return Response.success();
    }
}
