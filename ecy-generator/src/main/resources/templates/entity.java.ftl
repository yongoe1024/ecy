package ${package.Entity};

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class ${entity} {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    /**
     * ${field.comment}
     */
    <#if field.keyFlag>
    <#if field.keyIdentityFlag>
    @TableId(type = IdType.AUTO)
    <#elseif field.propertyType=="Long">
    @TableId(type = IdType.ASSIGN_ID)
    <#elseif field.propertyType=="String">
    @TableId(type = IdType.ASSIGN_UUID)
    </#if>
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