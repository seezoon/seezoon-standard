package com.seezoon.demo.application.sys.convertor;

import com.seezoon.demo.application.sys.dto.AddUserCmd;
import com.seezoon.demo.application.sys.dto.clientobject.UserCO;
import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.demo.domain.sys.valueobject.AddUserVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);


    AddUserVO toVO(AddUserCmd cmd);

    UserCO toCO(SysUserPO po);

    List<UserCO> toCO(List<SysUserPO> po);
}

