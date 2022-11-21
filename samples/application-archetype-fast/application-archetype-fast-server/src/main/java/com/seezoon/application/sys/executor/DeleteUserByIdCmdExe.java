package com.seezoon.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.application.sys.dto.DeleteUserByIdCmd;
import com.seezoon.ddd.dto.Response;
import com.seezoon.domain.sys.service.DeleteUserService;

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
        this.deleteUserService.delete(cmd.getUserId());
        return Response.success();
    }
}
