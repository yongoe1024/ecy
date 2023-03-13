package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.entity.Dict;
import com.yongoe.ecy.basic.entity.DictData;
import com.yongoe.ecy.basic.service.DictDataService;
import com.yongoe.ecy.basic.service.DictService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "数据字典")
@RestController
public class DictController {
    @Autowired
    private DictService dictService;
    @Autowired
    private DictDataService dictDataService;

    @Operation(summary = "查询字典,没权限")
    @PostMapping("/user/getdict")
    public R getDict(String name) {
        Dict dict = dictService.getOne(new QueryWrapper<Dict>().eq("name", name));
        List<DictData> dictData = dictDataService.list(new QueryWrapper<DictData>().eq("dict_id", dict.getId()).orderByDesc("sort"));
        Map<String, Object> map = new HashMap<>();
        map.put("list", dictData);
        map.put("type", dict.getType());
        return R.success().put(map);
    }

    @Operation(summary = "查询分页数据")
    @PostMapping("/basic/dict/page")
    public R page(Long current, Long size, @RequestBody Dict dict) {
        Page<Dict> page = dictService.getDictByPage(Page.of(current, size), dict);
        return R.success().put(new PageUtils(page));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/basic/dict/add")
    public R add(@RequestBody Dict dict) {
        dictService.save(dict);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/basic/dict/update")
    public R update(@RequestBody Dict dict) {
        dictService.updateById(dict);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/basic/dict/delete/{ids}")
    public R delete(@PathVariable List<String> ids) {
        dictService.removeByIds(ids);
        return R.success("删除成功");
    }

}