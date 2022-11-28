#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.google.common.collect.Lists;
import com.seezoon.ddd.dto.Response;
import ${package}.application.authentication.SecurityUtils;
import ${package}.application.sys.convertor.UserConvertor;
import ${package}.application.sys.dto.clientobject.PersonalInfoCO;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 个人信息
 * 
 * @author dfenghuang
 * @date 2022/11/24 23:16
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class PersonalInfoQryExe {

    private final SysUserRepository sysUserRepository;

    public Response<PersonalInfoCO> execute() {
        Integer userId = Objects.requireNonNull(SecurityUtils.getUserId());
        PersonalInfoCO personalInfoCO = new PersonalInfoCO();
        SysUserPO sysUserPO = sysUserRepository.find(userId);
        UserCO userCO = UserConvertor.INSTANCE.toCO(sysUserPO);
        personalInfoCO.setInfo(userCO);
        personalInfoCO.setRoles(Lists.newArrayList("role1", "role2"));
        personalInfoCO.setPermissions(Lists.newArrayList("p1", "p2"));
        return Response.success(personalInfoCO);
    }
}
