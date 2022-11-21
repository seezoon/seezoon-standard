package com.seezoon.application.sys.convertor;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.seezoon.application.sys.dto.AddUserCmd;
import com.seezoon.application.sys.dto.ModifyUserCmd;
import com.seezoon.application.sys.dto.clientobject.UserCO;
import com.seezoon.domain.sys.repository.po.SysUserPO;
import com.seezoon.domain.sys.valueobject.AddUserVO;
import com.seezoon.domain.sys.valueobject.ModifyUserVO;

@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    AddUserVO toVO(AddUserCmd cmd);

    ModifyUserVO toVO(ModifyUserCmd cmd);

    UserCO toCO(SysUserPO po);

    List<UserCO> toCO(List<SysUserPO> po);
}
