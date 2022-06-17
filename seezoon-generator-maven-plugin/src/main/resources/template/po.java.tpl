package ${baseRepositoryPackage}.${moduleName}.repository.po;

<#if importBigDecimal>
import java.math.BigDecimal;
</#if>
<#if importDate>
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
</#if>

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.seezoon.mybatis.repository.po.BasePO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${menuName!}
 *
 * @author seezoon-generator ${.now}
 */
@Getter
@Setter
@ToString
public class ${classNamePO} extends BasePO<${pkPlan.dataType.javaType()}> {

<#list columnPlans as columnPlan>
   <#if !columnPlan.defaultField>
      <#if columnPlan.fieldName?? && columnPlan.fieldName?length != 0>
     /**
      * ${columnPlan.fieldName!}
      */
      </#if>
      <#if !columnPlan.nullable>
        <#if columnPlan.stringType>
    @NotBlank
        <#else>
    @NotNull
        </#if>
      </#if>
      <#if columnPlan.stringType>
    @Size(max = ${columnPlan.maxLength?c})
      </#if>
    private ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName};

   </#if>
</#list>

<#if !pkPlan.defaultJavaPkName>
    @Override
    public ${pkPlan.dataType.javaType()} getId() {
        return ${pkPlan.javaFieldName};
    }

    @Override
    public void setId(${pkPlan.dataType.javaType()} ${pkPlan.javaFieldName}) {
        this.setId(${pkPlan.javaFieldName});
        this.${pkPlan.javaFieldName} = ${pkPlan.javaFieldName};
    }
</#if>
}