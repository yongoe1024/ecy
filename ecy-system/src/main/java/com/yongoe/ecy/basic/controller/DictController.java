package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DictReq;
import com.yongoe.ecy.basic.controller.vo.res.DictRes;
import com.yongoe.ecy.basic.convert.DictConvert;
import com.yongoe.ecy.basic.entity.Dict;
import com.yongoe.ecy.basic.entity.DictData;
import com.yongoe.ecy.basic.service.DictDataService;
import com.yongoe.ecy.basic.service.DictService;
import com.yongoe.ecy.config.aop.WebLog;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "数据字典")
@RestController
public class DictController {
    @Resource
    private DictService dictService;
    @Resource
    private DictDataService dictDataService;
    @Resource
    private DictConvert dictConvert;

    //@WebLog
    @Operation(summary = "查询分页数据")
    @PostMapping("/basic/dict/page")
    public R page(@RequestBody DictReq req) {
        Dict entity = dictConvert.req2Entity(req);
        Page<Dict> page = dictService.getDictByPage(Page.of(req.getCurrent(), req.getSize()), entity);
        Page<DictRes> resPage = dictConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(resPage));
    }

    @WebLog
    @Operation(summary = "添加数据")
    @PostMapping("/basic/dict/add")
    public R add(@RequestBody DictReq req) {
        Dict dict = dictConvert.req2Entity(req);
        dictService.save(dict);
        return R.success("添加成功");
    }

    @WebLog
    @Operation(summary = "修改数据")
    @PostMapping("/basic/dict/update")
    public R update(@RequestBody DictReq req) {
        Dict dict = dictConvert.req2Entity(req);
        dictService.updateById(dict);
        return R.success("修改成功");
    }

    @WebLog
    @Operation(summary = "删除数据")
    @PostMapping("/basic/dict/delete/{ids}")
    public R delete(@PathVariable List<Long> ids) {
        dictService.removeByIds(ids);
        for (Long id : ids) {
            dictDataService.remove(new LambdaQueryWrapper<DictData>().eq(DictData::getDictId, id));
        }
        return R.success("删除成功");
    }


}