#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.authentication;

import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Response;
import ${package}.application.authentication.dto.UsernamePasswordLoginCmd;
import ${package}.application.authentication.dto.clientobject.AuthorizationTokenCO;
import ${package}.application.authentication.executor.UsernamePasswordLoginCmdExe;

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
