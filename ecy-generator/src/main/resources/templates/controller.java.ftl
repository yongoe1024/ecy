package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.yongoe.ecy.utils.ExcelUtils;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ${moduleName}
 *
 * @author ${author}
 * @since ${createTime}
 */
@Tag(name = "${moduleName}")
@RestController
@RequestMapping("/${packageName}/${entity?lower_case}")
public class ${table.controllerName} {
    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size, @RequestBody ${entity} ${entity?uncap_first}) {
        Page<${entity}> page = ${table.serviceName?uncap_first}.get${entity}ByPage(Page.of(current, size), ${entity?uncap_first});
        return R.success().put(new PageUtils(page));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody ${entity} ${entity?uncap_first}) {
        ${table.serviceName?uncap_first}.save(${entity?uncap_first});
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody ${entity} ${entity?uncap_first}) {
        ${table.serviceName?uncap_first}.updateById(${entity?uncap_first});
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{ids}")
    public R delete(@PathVariable List<String> ids) {
        ${table.serviceName?uncap_first}.removeByIds(ids);
        return R.success("删除成功");
    }

    @Operation(summary = "导入数据")
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        List<${entity}> list = ExcelUtils.upload(file, ${entity}.class);
        ${table.serviceName?uncap_first}.saveBatch(list);
        return R.success("导入成功");
    }

    @Operation(summary = "导出数据")
    @PostMapping("/export")
    public void export(@RequestBody ${entity} ${entity?uncap_first}, HttpServletResponse response) {
        Page<${entity}> page = ${table.serviceName?uncap_first}.get${entity}ByPage(Page.of(-1, -1), ${entity?uncap_first});
        ExcelUtils.export(response, page.getRecords(), ${entity}.class);
    }
}