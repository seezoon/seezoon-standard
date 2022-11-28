#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import com.seezoon.ddd.dto.Response;
import com.seezoon.ddd.exception.Assertion;
import ${package}.application.sys.convertor.UserConvertor;
import ${package}.application.sys.dto.QryUserById;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 通过id查询用户信息
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class QryUserByIdExe {

    private final SysUserRepository sysUserRepository;

    /**
     * 入口函数
     *
     * @param qry
     */
    public Response<UserCO> execute(@NotNull @Valid QryUserById qry) {
        SysUserPO sysUserPO = sysUserRepository.find(qry.getUserId());
        Assertion.notNull(sysUserPO, "user id [" + qry.getUserId() + "] not exists");
        UserCO userCO = UserConvertor.INSTANCE.toCO(sysUserPO);
        return Response.success(userCO);
    }

}
