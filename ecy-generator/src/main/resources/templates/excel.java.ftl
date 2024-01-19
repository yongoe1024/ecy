package ${package.Controller}.vo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<#list table.importPackages as pkg>
import ${pkg};
</#list>

import java.time.LocalDateTime;

/**
 * ${moduleName}
 *
 * @author ${author}
 * @since ${createTime}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ${entity}Excel {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if !field.keyFlag>
    @Excel(name = "${field.comment}", width = 20)
    </#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->
}