package ${package.Mapper};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ${moduleName}
 *
 * @author ${author}
 * @since ${createTime}
 */
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    Page<${entity}> get${entity}ByPage(Page<${entity}> page, @Param("${entity?uncap_first}") ${entity} ${entity?uncap_first});

}
