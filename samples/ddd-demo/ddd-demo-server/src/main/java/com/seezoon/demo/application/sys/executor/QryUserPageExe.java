package com.seezoon.demo.application.sys.executor;

import com.github.pagehelper.PageSerializable;
import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.convertor.UserConvertor;
import com.seezoon.demo.application.sys.dto.QryUserPage;
import com.seezoon.demo.application.sys.dto.clientobject.UserCO;
import com.seezoon.demo.domain.sys.repository.SysUserRepository;
import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.demo.domain.sys.repository.po.SysUserPOCondition;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 分页查询
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class QryUserPageExe {

    private final SysUserRepository sysUserRepository;

    /**
     * 入口函数
     *
     * @param qry
     */
    public Response<Page<UserCO>> execute(@NotNull @Valid QryUserPage qry) {
        // 组装查询条件
        SysUserPOCondition condition = new SysUserPOCondition();
        condition.setUsername(qry.getUsername());
        condition.setMobile(qry.getMobile());
        condition.setName(qry.getName());

        PageSerializable<SysUserPO> page = sysUserRepository.find(condition, qry.getPage(),
                qry.getPageSize());

        return Response.success(new Page<>(page.getTotal(), UserConvertor.INSTANCE.toCO(page.getList())));
    }

}
