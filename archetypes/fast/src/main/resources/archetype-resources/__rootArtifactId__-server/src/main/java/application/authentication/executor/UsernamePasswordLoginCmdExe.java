#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.authentication.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.dto.Response;
import ${package}.application.authentication.dto.UsernamePasswordLoginCmd;
import ${package}.application.authentication.dto.clientobject.AuthorizationTokenCO;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.domain.sys.service.LoginTokenService;
import ${package}.infrastructure.error.ErrorCode;
import ${package}.infrastructure.properties.SeezoonProperties;
import ${package}.infrastructure.security.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 账号密码登录
 * 
 * @author dfenghuang
 * @date 2022/10/12 12:50
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class UsernamePasswordLoginCmdExe {

    private final LoginTokenService loginTokenService;
    private final SysUserRepository sysUserRepository;
    private final SeezoonProperties seezoonProperties;

    public Response<AuthorizationTokenCO> execute(@NotNull @Valid UsernamePasswordLoginCmd cmd) {
        SysUserPO sysUserPO = sysUserRepository.findByUsername(cmd.getUsername());
        if (sysUserPO == null) {
            return Response.error(ErrorCode.USER_PASSWD_WRONG.code(), ErrorCode.USER_PASSWD_WRONG.msg());
        }
        boolean matches = PasswordEncoder.matches(cmd.getPassword(), sysUserPO.getPassword());
        if (matches) {
            String token = loginTokenService.generateToken(sysUserPO.getUserId(),
                seezoonProperties.getApp().getLoginExpire().getSeconds());
            return Response.success(new AuthorizationTokenCO(token));
        } else {
            return Response.error(ErrorCode.USER_PASSWD_WRONG.code(), ErrorCode.USER_PASSWD_WRONG.msg());
        }
    }
}
