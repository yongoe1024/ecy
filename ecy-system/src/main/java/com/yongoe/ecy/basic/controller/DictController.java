package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DictReqVo;
import com.yongoe.ecy.basic.controller.vo.res.DictDataResVo;
import com.yongoe.ecy.basic.controller.vo.res.DictResVo;
import com.yongoe.ecy.basic.convert.DictConvert;
import com.yongoe.ecy.basic.convert.DictDataConvert;
import com.yongoe.ecy.basic.entity.Dict;
import com.yongoe.ecy.basic.entity.DictData;
import com.yongoe.ecy.basic.service.DictDataService;
import com.yongoe.ecy.basic.service.DictService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
    @Resource
    private DictService dictService;
    @Resource
    private DictDataService dictDataService;
    @Resource
    private DictConvert dictConvert;
    @Resource
    private DictDataConvert dictDataConvert;

    @Operation(summary = "查询字典,没权限")
    @PostMapping("/user/getdict")
    public R getDict(String name) {
        Dict dict = dictService.getOne(new LambdaQueryWrapper<Dict>().eq(Dict::getName, name));
        List<DictData> dictData = dictDataService.list(new LambdaQueryWrapper<DictData>()
                .eq(DictData::getDictId, dict.getId())
                .orderByDesc(DictData::getSort));
        List<DictDataResVo> voList = dictDataConvert.entity2ResList(dictData);
        Map<String, Object> map = new HashMap<>();
        map.put("list", voList);
        map.put("type", dict.getType());
        return R.success().put(map);
    }

    @Operation(summary = "查询分页数据")
    @PostMapping("/basic/dict/page")
    public R page(Long current, Long size, @RequestBody DictReqVo reqVo) {
        Dict entity = dictConvert.req2Entity(reqVo);
        Page<Dict> page = dictService.getDictByPage(Page.of(current, size), entity);
        Page<DictResVo> voPage = dictConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(voPage));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/basic/dict/add")
    public R add(@RequestBody DictReqVo reqVo) {
        Dict dict = dictConvert.req2Entity(reqVo);
        dictService.save(dict);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/basic/dict/update")
    public R update(@RequestBody DictReqVo reqVo) {
        Dict dict = dictConvert.req2Entity(reqVo);
        dictService.updateById(dict);
        return R.success("修改成功");
    }

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