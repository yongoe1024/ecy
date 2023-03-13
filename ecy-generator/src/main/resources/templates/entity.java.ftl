package ${package.Entity};

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
<#list table.importPackages as pkg>
import ${pkg};
</#list>
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ${moduleName}
 *
 * @author ${author}
 * @since ${createTime}
 */
@Data
@TableName("${table.name}")
@Schema(name = "${moduleName}")
public class ${entity} {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if !field.keyFlag>
    @Excel(name = "${field.comment}", width = 20)
    <#else >
    @TableId(value="id",type = IdType.AUTO)
    </#if>
    <#if field.name=="create_time" || field.name=="create_by">
    @TableField(fill = FieldFill.INSERT)
    <#elseif field.name=="update_time" || field.name=="update_by">
    @TableField(fill = FieldFill.INSERT_UPDATE)
    </#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->
}