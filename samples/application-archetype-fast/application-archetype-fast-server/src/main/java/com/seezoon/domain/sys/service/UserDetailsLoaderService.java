package com.seezoon.domain.sys.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.seezoon.ddd.exception.BizException;
import com.seezoon.domain.sys.repository.SysUserRepository;
import com.seezoon.domain.sys.repository.po.SysUserPO;
import com.seezoon.domain.sys.valueobject.UserStatusVO;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.security.UserDetailsLoader;
import com.seezoon.infrastructure.security.UserInfoDetails;

import lombok.RequiredArgsConstructor;

/**
 * @author dfenghuang
 * @date 2022/10/12 00:49
 */
@Service
@RequiredArgsConstructor
public class UserDetailsLoaderService implements UserDetailsLoader {

    private final SysUserRepository sysUserRepository;

    @Override
    public UserInfoDetails loadByUserId(Integer userId) {
        SysUserPO sysUserPO = sysUserRepository.find(userId);
        if (null == sysUserPO) {
            throw new BizException(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        if (!UserStatusVO.valid(sysUserPO.getStatus())) {
            throw new BizException(ErrorCode.USER_STATUS_INVALID.code(), ErrorCode.USER_STATUS_INVALID.msg());
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // authorities.add(new UserGrantedAuthority("admin", true));
        // 关键信息checkSum ,修改之后可以保证token 失效
        String checkSum = DigestUtils.sha1Hex(sysUserPO.getUserId() + StringUtils.trimToEmpty(sysUserPO.getPassword()));
        UserInfoDetails userInfoDetails =
            new UserInfoDetails(sysUserPO.getUsername(), sysUserPO.getUserId(), checkSum, authorities);
        return userInfoDetails;
    }

}
