package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * ${moduleName}
 *
 * @author ${author}
 * @since ${createTime}
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    Page<${entity}> get${entity}ByPage(Page<${entity}> page, ${entity} ${entity?uncap_first});

}
