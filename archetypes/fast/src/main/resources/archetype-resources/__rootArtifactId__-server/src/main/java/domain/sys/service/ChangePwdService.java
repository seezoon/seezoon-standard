#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.service;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.exception.BizException;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.infrastructure.error.ErrorCode;
import ${package}.infrastructure.security.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 修改密码
 *
 * @author dfenghuang
 * @date 2022/11/14 10:26
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Validated
public class ChangePwdService {

    private final SysUserRepository sysUserRepository;

    /**
     * 不验证原密码
     *
     * @param userId
     * @param password
     */
    public void changePwd(@NotNull Integer userId, @NotBlank @Min(6) String password) {
        SysUserPO sysUserPO = sysUserRepository.find(userId);
        if (null == sysUserPO) {
            throw new BizException(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        this.updatePwd(userId, password);
    }

    public void changePwd(@NotNull Integer userId, @NotBlank @Min(6) String password,
        @NotBlank @Min(6) String oldPassword) {
        SysUserPO sysUserPO = sysUserRepository.find(userId);
        if (null == sysUserPO) {
            throw new BizException(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        // 验证原密码
        if (!Objects.equals(PasswordEncoder.encode(oldPassword), sysUserPO.getPassword())) {
            throw new BizException(ErrorCode.OLD_PASSWD_WRONG.code(), ErrorCode.OLD_PASSWD_WRONG.msg());
        }
        this.updatePwd(userId, password);
    }

    private void updatePwd(Integer userId, String password) {
        SysUserPO toUpdate = new SysUserPO();
        toUpdate.setUserId(userId);
        toUpdate.setPassword(PasswordEncoder.encode(password));
        this.sysUserRepository.updateSelective(toUpdate);
    }
}
