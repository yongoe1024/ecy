package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yongoe.ecy.basic.entity.Department;
import com.yongoe.ecy.basic.service.DepartmentService;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "部门")
@RestController
@RequestMapping("/basic/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = "查询数据")
    @PostMapping("/tree")
    public R tree() {
        return R.success().put(departmentService.getTree());
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody Department department) {
        departmentService.save(department);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody Department department) {
        departmentService.updateById(department);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        List<Department> list = departmentService.list(new QueryWrapper<Department>().eq("parent_id", id));
        if (!CollectionUtils.isEmpty(list)) {
            return R.error("请先清空子目录");
        }
        departmentService.removeById(id);
        return R.success("删除成功");
    }

}