package com.seezoon.demo.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.convertor.UserConvertor;
import com.seezoon.demo.application.sys.dto.AddUserCmd;
import com.seezoon.demo.domain.sys.service.AddUserService;
import com.seezoon.demo.domain.sys.valueobject.AddUserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 添加用户
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class AddUserCmdExe {

    private final AddUserService addUserService;

    /**
     * 入口函数
     *
     * @param cmd
     */
    public Response execute(@NotNull @Valid AddUserCmd cmd) {
        AddUserVO vo = UserConvertor.INSTANCE.toVO(cmd);
        addUserService.add(vo);
        return Response.success();
    }
}
