package com.seezoon.domain.sys.service;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.exception.ExceptionFactory;
import com.seezoon.domain.sys.repository.SysUserRepository;
import com.seezoon.domain.sys.repository.po.SysUserPO;
import com.seezoon.domain.sys.valueobject.ModifyUserVO;
import com.seezoon.infrastructure.error.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 修改用户信息
 * 
 * @author dfenghuang
 * @date 2022/11/21 12:38
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
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
        SysUserPO po = new SysUserPO();
        po.setUserId(vo.getUserId());
        po.setUsername(vo.getUsername());
        po.setName(vo.getName());
        po.setMobile(vo.getMobile());
        po.setPhoto(vo.getPhoto());
        po.setEmail(vo.getEmail());
        po.setStatus(vo.getStatus());
        sysUserRepository.updateSelective(po);
    }
}
