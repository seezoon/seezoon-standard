package com.seezoon.demo.domain.sys.service;

import com.seezoon.demo.domain.sys.repository.SysUserRepository;
import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.demo.domain.sys.valueobject.AddUserVO;
import com.seezoon.demo.domain.sys.valueobject.UserStatusVO;
import com.seezoon.demo.infrastructure.security.PasswordEncoder;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 添加用户
 *
 * @author dfenghuang
 * @date 2022/11/21 12:38
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@Validated
public class AddUserService {

    private final SysUserRepository sysUserRepository;

    public Integer add(@NotNull @Valid AddUserVO vo) {
        SysUserPO po = new SysUserPO();
        po.setUsername(vo.getUsername());
        po.setName(vo.getName());
        if (StringUtils.isNotBlank(vo.getPassword())) {
            po.setPassword(PasswordEncoder.encode(vo.getPassword()));
        }
        po.setMobile(vo.getMobile());
        po.setPhoto(vo.getPhoto());
        po.setEmail(vo.getEmail());
        po.setStatus(UserStatusVO.VALID);
        sysUserRepository.save(po);
        return Objects.requireNonNull(po.getUserId());
    }
}
