package com.seezoon.application.authentication;

import com.seezoon.application.authentication.dto.UsernamePasswordLoginCmd;
import com.seezoon.application.authentication.dto.clientobject.AuthorizationTokenCO;
import com.seezoon.application.authentication.executor.UsernamePasswordLoginCmdExe;
import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Response;

import lombok.RequiredArgsConstructor;

/**
 * @author dfenghuang
 * @date 2022/10/12 12:47
 */
@ApplicationService
@RequiredArgsConstructor
public class LoginApplicationService {

    private final UsernamePasswordLoginCmdExe usernamePasswordLoginCmdExe;

    public Response<AuthorizationTokenCO> usernamePasswordLogin(UsernamePasswordLoginCmd cmd) {
        return usernamePasswordLoginCmdExe.execute(cmd);
    }
}
