package ${baseRepositoryPackage}.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.seezoon.mybatis.repository.mapper.CrudMapper;
import ${baseRepositoryPackage}.repository.po.${classNamePO};

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@Mapper
public interface ${className}Mapper extends CrudMapper<${classNamePO}, ${pkPlan.dataType.javaType()}> {

}