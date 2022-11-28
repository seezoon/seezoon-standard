package com.seezoon.demo.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.authentication.SecurityUtils;
import com.seezoon.demo.application.sys.dto.DeleteUserByIdCmd;
import com.seezoon.demo.domain.sys.service.DeleteUserService;
import com.seezoon.demo.infrastructure.error.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 删除用户
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class DeleteUserByIdCmdExe {

    private final DeleteUserService deleteUserService;

    /**
     * 入口函数
     *
     * @param cmd
     */
    public Response execute(@NotNull @Valid DeleteUserByIdCmd cmd) {
        if (SecurityUtils.isSuperAdmin(cmd.getUserId())) {
            return Response.error(ErrorCode.SUPER_ADMIN_NOT_ALLOW_DELETE.code(),
                ErrorCode.SUPER_ADMIN_NOT_ALLOW_DELETE.msg());
        }
        this.deleteUserService.delete(cmd.getUserId());
        return Response.success();
    }
}
