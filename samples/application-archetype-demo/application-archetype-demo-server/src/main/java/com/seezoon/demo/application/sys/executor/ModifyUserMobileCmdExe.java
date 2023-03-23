package com.seezoon.demo.application.sys.executor;

import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.dto.ModifyUserMobileCmd;
import com.seezoon.demo.domain.sys.service.ModifyUserMobileService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 修改用户手机号
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class ModifyUserMobileCmdExe {

    private final ModifyUserMobileService modifyUserMobileService;

    /**
     * 入口函数
     *
     * @param cmd
     */
    public Response execute(@NotNull @Valid ModifyUserMobileCmd cmd) {
        // 如果参数复杂可以将cmd转换为领域层中的值对象或者实体
        modifyUserMobileService.modifyUserMobile(cmd.getUserId(), cmd.getMobile(), cmd.getVerifyCode());
        return Response.success();
    }

}
