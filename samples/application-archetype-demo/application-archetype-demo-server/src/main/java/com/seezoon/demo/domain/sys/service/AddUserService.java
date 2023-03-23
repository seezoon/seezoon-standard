package com.seezoon.demo.domain.sys.service;

import com.seezoon.demo.domain.sys.repository.SysUserRepository;
import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.demo.domain.sys.valueobject.AddUserVO;
import com.seezoon.mybatis.repository.constants.Constants;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@Validated
public class AddUserService {

    private final SysUserRepository sysUserRepository;

    public Integer addUser(@NotNull @Valid AddUserVO vo) {
        SysUserPO po = new SysUserPO();
        po.setUsername(vo.getUsername());
        po.setName(vo.getName());
        po.setMobile(vo.getMobile());
        po.setPhoto(vo.getPhoto());
        po.setEmail(vo.getEmail());
        po.setStatus(Constants.NORMAL);
        sysUserRepository.save(po);
        return po.getUserId();
    }
}
