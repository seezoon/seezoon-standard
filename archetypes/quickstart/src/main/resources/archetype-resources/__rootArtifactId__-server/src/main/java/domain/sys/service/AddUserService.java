#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.service;

import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.domain.sys.valueobject.AddUserVO;
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
