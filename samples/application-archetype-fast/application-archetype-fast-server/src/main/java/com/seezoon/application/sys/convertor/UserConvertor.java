package com.seezoon.application.sys.convertor;

import com.seezoon.application.sys.dto.AddUserCmd;
import com.seezoon.application.sys.dto.clientobject.UserCO;
import com.seezoon.domain.sys.repository.po.SysUserPO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    SysUserPO toPOForAddUser(AddUserCmd cmd);

    UserCO toCO(SysUserPO po);

    List<UserCO> toCO(List<SysUserPO> po);
}

