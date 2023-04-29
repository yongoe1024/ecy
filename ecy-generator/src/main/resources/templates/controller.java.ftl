package ${package.Controller};

import ${package.Service}.${table.serviceName};
import com.yongoe.ecy.${packageName}.controller.vo.excel.${entity}ExcelVo;
import com.yongoe.ecy.${packageName}.controller.vo.req.${entity}ReqVo;
import com.yongoe.ecy.${packageName}.controller.vo.res.${entity}ResVo;
import com.yongoe.ecy.${packageName}.convert.${entity}Convert;
import com.yongoe.ecy.${packageName}.entity.${entity};
import com.yongoe.ecy.utils.ExcelUtils;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;
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
    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};
    @Resource
    private ${entity}Convert ${entity?uncap_first}Convert;

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size, @RequestBody ${entity}ReqVo reqVo) {
        ${entity} entity = ${entity?uncap_first}Convert.req2Entity(reqVo);
        Page<${entity}> page = ${table.serviceName?uncap_first}.get${entity}ByPage(Page.of(current, size), entity);
        Page<${entity}ResVo> voPage = ${entity?uncap_first}Convert.entity2ResPage(page);
        return R.success().put(new PageUtils(voPage));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody ${entity}ReqVo reqVo) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Convert.req2Entity(reqVo);
        ${table.serviceName?uncap_first}.save(${entity?uncap_first});
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody ${entity}ReqVo reqVo) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Convert.req2Entity(reqVo);
        ${table.serviceName?uncap_first}.updateById(${entity?uncap_first});
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{ids}")
    public R delete(@PathVariable List<Long> ids) {
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
    public void export(@RequestBody ${entity}ReqVo reqVo, HttpServletResponse response) {
        ${entity} entity = ${entity?uncap_first}Convert.req2Entity(reqVo);
        Page<${entity}> page = ${table.serviceName?uncap_first}.get${entity}ByPage(Page.of(-1, -1), entity);
        List<${entity}ExcelVo> list = ${entity?uncap_first}Convert.entity2ExcelPage(page).getRecords();
        ExcelUtils.export(response, list, ${entity}ExcelVo.class);
    }
}