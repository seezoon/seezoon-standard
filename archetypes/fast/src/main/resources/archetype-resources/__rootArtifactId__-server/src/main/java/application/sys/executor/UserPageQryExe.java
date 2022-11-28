#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.github.pagehelper.PageSerializable;
import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.convertor.UserConvertor;
import ${package}.application.sys.dto.UserPageQry;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.domain.sys.repository.po.SysUserPOCondition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页查询
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserPageQryExe {

    private final SysUserRepository sysUserRepository;

    /**
     * 入口函数
     *
     * @param qry
     */
    public Response<Page<UserCO>> execute(@NotNull @Valid UserPageQry qry) {
        // 组装查询条件
        SysUserPOCondition condition = new SysUserPOCondition();
        condition.setUsername(qry.getUsername());
        condition.setMobile(qry.getMobile());
        condition.setName(qry.getName());

        PageSerializable<SysUserPO> page = sysUserRepository.find(condition, qry.getPage(), qry.getPageSize());

        return Response.success(new Page<>(page.getTotal(), UserConvertor.INSTANCE.toCO(page.getList())));
    }

}
