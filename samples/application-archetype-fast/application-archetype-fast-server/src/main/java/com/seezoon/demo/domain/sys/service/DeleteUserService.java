package com.seezoon.demo.domain.sys.service;

import com.seezoon.ddd.exception.BizException;
import com.seezoon.demo.domain.sys.repository.SysUserRepository;
import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.demo.infrastructure.error.ErrorCode;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 删除用户
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@Validated
public class DeleteUserService {

    private final SysUserRepository sysUserRepository;

    public void delete(@NotNull Integer userId) {
        SysUserPO sysUserPO = sysUserRepository.find(userId);
        if (null == sysUserPO) {
            throw new BizException(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        sysUserRepository.delete(userId);
    }

}
