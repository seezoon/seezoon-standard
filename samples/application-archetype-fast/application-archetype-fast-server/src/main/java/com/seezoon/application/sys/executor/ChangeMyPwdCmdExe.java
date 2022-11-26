package com.seezoon.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.application.authentication.SecurityUtils;
import com.seezoon.application.sys.dto.ChangeMyPwdCmd;
import com.seezoon.ddd.dto.Response;
import com.seezoon.domain.sys.service.ChangePwdService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 修改个人用户密码
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class ChangeMyPwdCmdExe {

    private final ChangePwdService changePwdService;

    /**
     * 入口函数
     *
     * @param cmd
     */
    public Response execute(@NotNull @Valid ChangeMyPwdCmd cmd) {
        this.changePwdService.changePwd(SecurityUtils.getUserId(), cmd.getPassword(), cmd.getOldPassword());
        return Response.success();
    }
}
