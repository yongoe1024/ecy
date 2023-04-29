package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DictDataReqVo;
import com.yongoe.ecy.basic.controller.vo.res.DictDataResVo;
import com.yongoe.ecy.basic.convert.DictDataConvert;
import com.yongoe.ecy.basic.entity.DictData;
import com.yongoe.ecy.basic.service.DictDataService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典-数据
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "数据字典-数据")
@RestController
@RequestMapping("/basic/dict/data")
public class DictDataController {
    @Resource
    private DictDataService dictDataService;
    @Resource
    private DictDataConvert dictDataConvert;

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size, @RequestBody DictDataReqVo reqVo) {
        DictData entity = dictDataConvert.req2Entity(reqVo);
        Page<DictData> page = dictDataService.getDictDataByPage(Page.of(current, size), entity);
        Page<DictDataResVo> voPage = dictDataConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(voPage));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody DictDataReqVo reqVo) {
        DictData dictData = dictDataConvert.req2Entity(reqVo);
        dictDataService.save(dictData);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody DictDataReqVo reqVo) {
        DictData dictData = dictDataConvert.req2Entity(reqVo);
        dictDataService.updateById(dictData);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{ids}")
    public R delete(@PathVariable List<Long> ids) {
        dictDataService.removeByIds(ids);
        return R.success("删除成功");
    }

}