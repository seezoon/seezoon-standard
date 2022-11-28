#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.service;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.domain.sys.valueobject.AddUserVO;
import ${package}.domain.sys.valueobject.UserStatusVO;
import ${package}.infrastructure.security.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 添加用户
 * 
 * @author dfenghuang
 * @date 2022/11/21 12:38
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
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
