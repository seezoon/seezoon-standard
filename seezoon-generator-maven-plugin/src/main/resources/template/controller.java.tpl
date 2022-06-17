package ${baseControllerPackage}.${moduleName}.web;
<#list columnPlans as columnPlan>
  <#if columnPlan.uniqueField>

import java.util.Objects;
    <#break>
  </#if>
</#list>
import javax.validation.Valid;
import javax.validation.Valid;
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

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import com.github.pagehelper.PageSerializable;
import ${baseRepositoryPackage}.${moduleName}.repository.${className}Repository;
import ${baseRepositoryPackage}.${moduleName}.repository.po.${classNamePO};
import ${baseRepositoryPackage}.${moduleName}.repository.po.${classNamePO}Condition;
import com.seezoon.ddd.dto.Response;

import lombok.RequiredArgsConstructor;

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@RestController
@RequestMapping("/${moduleName}/${functionName}")
@RequiredArgsConstructor
@Validated
public class ${className}Controller {

    private final ${className}Repository ${className?uncap_first}Repository;

    @GetMapping("/query/{${pkPlan.javaFieldName}}")
    public Response<${classNamePO}> query(@PathVariable ${pkPlan.dataType.javaType()} ${pkPlan.javaFieldName}) {
        ${classNamePO} ${classNamePO?uncap_first} = ${className?uncap_first}Repository.find(${pkPlan.javaFieldName});
        return Response.success(${classNamePO?uncap_first});
    }

    @PostMapping("/query")
    public Response<PageSerializable<${classNamePO}>> query(@Valid @RequestBody ${classNamePO}Condition condition) {
        PageSerializable<${classNamePO}> pageSerializable = ${className?uncap_first}Repository.find(condition, condition.getPage(), condition.getPageSize());
        return Response.success(pageSerializable);
    }

    @PostMapping(value = "/save")
    public Response save(@Valid @RequestBody ${classNamePO} ${classNamePO?uncap_first}) {
        ${className?uncap_first}Repository.save(${classNamePO?uncap_first});
        return Response.success();
    }

    @PostMapping(value = "/update")
    public Response update(@Valid @RequestBody ${classNamePO} ${classNamePO?uncap_first}) {
        ${className?uncap_first}Repository.updateSelective(${classNamePO?uncap_first});
        return Response.success();
    }

    @PostMapping(value = "/delete")
    public Response delete(@RequestParam ${pkPlan.dataType.javaType()} ${pkPlan.javaFieldName}) {
        ${className?uncap_first}Repository.delete(${pkPlan.javaFieldName});
        return Response.success();
    }

    <#list columnPlans as columnPlan>
      <#if columnPlan.uniqueField>
    @PostMapping(value = "/check_${columnPlan.underScoreFieldName}")
    public Response<Boolean> check${columnPlan.javaFieldName?cap_first}(@RequestParam(required = false) ${pkPlan.dataType.javaType()} ${pkPlan.javaFieldName},
        ${columnPlan.stringType?string("@NotBlank","@NotNull")} @RequestParam ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName}) {
        ${classNamePO} ${classNamePO?uncap_first} = this.${className?uncap_first}Repository.findBy${columnPlan.javaFieldName?cap_first}(${columnPlan.javaFieldName});
                return Response.success(null == ${classNamePO?uncap_first} || Objects.equals(${classNamePO?uncap_first}.get${pkPlan.javaFieldName?cap_first}(), ${pkPlan.javaFieldName}));
    }

      </#if>
    </#list>
}
