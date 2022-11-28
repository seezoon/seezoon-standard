package com.seezoon.demo.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.convertor.UserConvertor;
import com.seezoon.demo.application.sys.dto.UserByIdQry;
import com.seezoon.demo.application.sys.dto.clientobject.UserCO;
import com.seezoon.demo.domain.sys.repository.SysUserRepository;
import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.demo.infrastructure.error.ErrorCode;

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
