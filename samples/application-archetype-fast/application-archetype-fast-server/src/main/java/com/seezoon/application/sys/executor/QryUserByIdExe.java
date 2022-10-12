package com.seezoon.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.application.authentication.SecurityUtils;
import com.seezoon.application.sys.convertor.UserConvertor;
import com.seezoon.application.sys.dto.QryUserById;
import com.seezoon.application.sys.dto.clientobject.UserCO;
import com.seezoon.ddd.dto.Response;
import com.seezoon.ddd.exception.Assertion;
import com.seezoon.domain.sys.repository.SysUserRepository;
import com.seezoon.domain.sys.repository.po.SysUserPO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        Integer userId = SecurityUtils.getUserId();
        return Response.success(userCO);
    }

}
