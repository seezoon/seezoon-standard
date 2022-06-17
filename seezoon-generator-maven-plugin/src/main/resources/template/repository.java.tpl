package ${baseRepositoryPackage}.${moduleName}.repository;

<#list columnPlans as columnPlan>
  <#if columnPlan.uniqueField && columnPlan.stringType>
import javax.validation.constraints.NotBlank;
    <#break>
  </#if>
</#list>
<#list columnPlans as columnPlan>
  <#if columnPlan.uniqueField && !columnPlan.stringType>
import javax.validation.constraints.NotNull;
    <#break>
  </#if>
</#list>

import org.springframework.stereotype.Repository;

import com.seezoon.mybatis.repository.AbstractCrudRepository;
import ${baseRepositoryPackage}.${moduleName}.repository.mapper.${className}Mapper;
import ${baseRepositoryPackage}.${moduleName}.repository.po.${classNamePO};
<#list columnPlans as columnPlan>
  <#if columnPlan.uniqueField>
import ${baseRepositoryPackage}.${moduleName}.repository.po.${classNamePO}Condition;
import org.springframework.transaction.annotation.Transactional;

    <#break>
  </#if>
</#list>

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@Repository
public class ${className}Repository extends AbstractCrudRepository<${className}Mapper, ${classNamePO}, ${pkPlan.dataType.javaType()}> {
    <#list columnPlans as columnPlan>
      <#if columnPlan.uniqueField>

    @Transactional(readOnly = true)
    public ${classNamePO} findBy${columnPlan.javaFieldName?cap_first}(${columnPlan.stringType?string("@NotBlank","@NotNull")} ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName}) {
        ${classNamePO}Condition ${classNamePO?uncap_first}Condition = new ${classNamePO}Condition();
        ${classNamePO?uncap_first}Condition.set${columnPlan.javaFieldName?cap_first}(${columnPlan.javaFieldName});
        return this.findOne(${classNamePO?uncap_first}Condition);
    }
      </#if>
    </#list>
}
