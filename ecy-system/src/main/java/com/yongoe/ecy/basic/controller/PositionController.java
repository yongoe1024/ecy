package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.entity.Position;
import com.yongoe.ecy.basic.service.PositionService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "职位")
@RestController
@RequestMapping("/basic/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size, @RequestBody Position position) {
        Page<Position> page = positionService.getPositionByPage(Page.of(current, size), position);
        return R.success().put(new PageUtils(page));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody Position position) {
        positionService.save(position);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody Position position) {
        positionService.updateById(position);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{ids}")
    public R delete(@PathVariable List<String> ids) {
        positionService.removeByIds(ids);
        return R.success("删除成功");
    }

}