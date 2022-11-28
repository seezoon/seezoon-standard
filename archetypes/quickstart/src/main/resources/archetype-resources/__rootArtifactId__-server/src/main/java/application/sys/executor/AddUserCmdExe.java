#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.convertor.UserConvertor;
import ${package}.application.sys.dto.AddUserCmd;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.domain.sys.valueobject.UserStatus;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 添加用户
 * 职责：完成参数校验，事务逻辑，要考虑事务粒度尽量要小
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class AddUserCmdExe {

    private final SysUserRepository sysUserRepository;

    /**
     * 入口函数
     *
     * @param cmd
     */
    public Response execute(@NotNull @Valid AddUserCmd cmd) {
        SysUserPO sysUserPO = UserConvertor.INSTANCE.toPOForAddUser(cmd);
        sysUserPO.setStatus(UserStatus.VALID);
        this.sysUserRepository.save(sysUserPO);
        return Response.success();
    }
}
