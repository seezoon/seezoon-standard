package ${baseRepositoryPackage}.${moduleName}.repository.po;

<#if importDate>
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
</#if>

import com.seezoon.mybatis.repository.po.PagePOCondition;
<#if sortable>
import com.seezoon.mybatis.repository.sort.annotation.SortField;
</#if>
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@Getter
@Setter
@ToString
<#if sortable>
<#assign firstItem = true>
@SortField({<#list columnPlans as columnPlan><#if columnPlan.sortable>${firstItem?string("",",")}"${columnPlan.javaFieldName}:${defaultTableAliasPrefix}${columnPlan.dbColumnName}"<#assign firstItem = false></#if></#list>})
</#if>
public class ${classNamePO}Condition extends PagePOCondition {

    <#list columnPlans as columnPlan>
        <#if columnPlan.search>
      <#if columnPlan.fieldName?? && columnPlan.fieldName?length != 0>
    /**
     * ${columnPlan.fieldName!}
     */
      </#if>
    private ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName};
        </#if>
    </#list>

}