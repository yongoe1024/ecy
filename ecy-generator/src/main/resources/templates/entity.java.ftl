package ${package.Entity};

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@TableName("${table.name}")
@Schema(name = "${moduleName}")
public class ${entity} {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
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