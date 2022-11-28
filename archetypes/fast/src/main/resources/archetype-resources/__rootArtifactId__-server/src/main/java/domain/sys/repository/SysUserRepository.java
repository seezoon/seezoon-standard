#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.repository;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ${package}.domain.sys.repository.mapper.SysUserMapper;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.domain.sys.repository.po.SysUserPOCondition;
import com.seezoon.mybatis.repository.AbstractCrudRepository;

/**
 * 用户信息
 * 
 * @author seezoon-generator 2022年6月16日 下午11:12:31
 */
@Repository
public class SysUserRepository extends AbstractCrudRepository<SysUserMapper, SysUserPO, Integer> {

    @Transactional(readOnly = true)
    public SysUserPO findByUsername(@NotBlank String username) {
        SysUserPOCondition sysUserPOCondition = new SysUserPOCondition();
        sysUserPOCondition.setUsername(username);
        return this.findOne(sysUserPOCondition);
    }
}
