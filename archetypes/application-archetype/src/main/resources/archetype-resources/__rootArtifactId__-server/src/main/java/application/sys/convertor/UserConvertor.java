#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.convertor;

import ${package}.application.sys.dto.AddUserCmd;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.domain.sys.repository.po.SysUserPO;
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

