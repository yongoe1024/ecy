package ${package.Controller}.vo.req;

import com.yongoe.ecy.utils.BaseEntity;
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
@Schema(name = "${moduleName}Req")
public class ${entity}Req  extends BaseEntity {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    /**
     * ${field.comment}
     */
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}