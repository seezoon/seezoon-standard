package com.seezoon.domain.sys.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.exception.BizException;
import com.seezoon.domain.sys.repository.SysUserRepository;
import com.seezoon.domain.sys.repository.po.SysUserPO;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.security.PasswordEncoder;

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

    public void changePwd(@NotNull Integer userId, @NotBlank @Min(6) String password) {
        SysUserPO sysUserPO = sysUserRepository.find(userId);
        if (null == sysUserPO) {
            throw new BizException(ErrorCode.USER_NOT_EXISTS.code(), "userId [" + userId + "] not exists");
        }
        SysUserPO toUpdate = new SysUserPO();
        toUpdate.setUserId(userId);
        toUpdate.setPassword(PasswordEncoder.encode(password));
        this.sysUserRepository.updateSelective(toUpdate);
    }
}
