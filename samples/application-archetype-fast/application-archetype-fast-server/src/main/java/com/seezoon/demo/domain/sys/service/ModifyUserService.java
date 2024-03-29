package com.seezoon.demo.domain.sys.service;

import com.seezoon.ddd.exception.ExceptionFactory;
import com.seezoon.demo.domain.sys.repository.SysUserRepository;
import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.demo.domain.sys.valueobject.ModifyUserVO;
import com.seezoon.demo.infrastructure.error.ErrorCode;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 修改用户信息
 *
 * @author dfenghuang
 * @date 2022/11/21 12:38
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@Validated
public class ModifyUserService {

    private final SysUserRepository sysUserRepository;

    public void modify(@NotNull @Valid ModifyUserVO vo) {
        SysUserPO sysUserPO = sysUserRepository.find(vo.getUserId());
        if (null == sysUserPO) {
            throw ExceptionFactory.bizException(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        SysUserPO exists = sysUserRepository.findByUsername(vo.getUsername());
        if (null != exists && !Objects.equals(exists.getUserId(), exists.getUserId())) {
            throw ExceptionFactory.bizException(ErrorCode.USER_NAME_EXISTS.code(),
                    String.format(ErrorCode.USER_NAME_EXISTS.msg(), vo.getUsername()));
        }
        sysUserPO.setUserId(vo.getUserId());
        sysUserPO.setPhoto(vo.getPhoto());
        sysUserPO.setUsername(vo.getUsername());
        sysUserPO.setName(vo.getName());
        sysUserPO.setMobile(vo.getMobile());
        sysUserPO.setPhoto(vo.getPhoto());
        sysUserPO.setEmail(vo.getEmail());
        sysUserPO.setStatus(vo.getStatus());
        sysUserRepository.update(sysUserPO);
    }
}
