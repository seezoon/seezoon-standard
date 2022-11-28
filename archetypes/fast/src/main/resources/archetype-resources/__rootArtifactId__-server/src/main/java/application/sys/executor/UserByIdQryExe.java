#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.convertor.UserConvertor;
import ${package}.application.sys.dto.UserByIdQry;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.infrastructure.error.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过id查询用户信息
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserByIdQryExe {

    private final SysUserRepository sysUserRepository;

    /**
     * 入口函数
     *
     * @param qry
     */
    public Response<UserCO> execute(@NotNull @Valid UserByIdQry qry) {
        SysUserPO sysUserPO = sysUserRepository.find(qry.getUserId());
        if (null == sysUserPO) {
            return Response.error(ErrorCode.USER_NOT_EXISTS.code(), "user id [" + qry.getUserId() + "] not exists");
        }
        UserCO userCO = UserConvertor.INSTANCE.toCO(sysUserPO);
        return Response.success(userCO);
    }

}
