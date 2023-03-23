package com.seezoon.demo.application.sys.executor;

import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.convertor.UserConvertor;
import com.seezoon.demo.application.sys.dto.AddUserCmd;
import com.seezoon.demo.domain.sys.service.AddUserService;
import com.seezoon.demo.domain.sys.valueobject.AddUserVO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 添加用户
 * 职责：完成参数校验，事务逻辑，要考虑事务粒度尽量要小
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
        addUserService.addUser(vo);
        return Response.success();
    }
}
