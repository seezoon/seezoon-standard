package com.seezoon.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.application.sys.convertor.UserConvertor;
import com.seezoon.application.sys.dto.ModifyUserCmd;
import com.seezoon.ddd.dto.Response;
import com.seezoon.domain.sys.service.ModifyUserService;
import com.seezoon.domain.sys.valueobject.ModifyUserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 修改用户
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class ModifyUserCmdExe {

    private final ModifyUserService modifyUserService;

    /**
     * 入口函数
     *
     * @param cmd
     */
    public Response execute(@NotNull @Valid ModifyUserCmd cmd) {
        ModifyUserVO vo = UserConvertor.INSTANCE.toVO(cmd);
        modifyUserService.modify(vo);
        return Response.success();
    }
}
