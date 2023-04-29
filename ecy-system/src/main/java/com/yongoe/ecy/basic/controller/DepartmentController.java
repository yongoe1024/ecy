package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yongoe.ecy.basic.controller.vo.req.DepartmentReqVo;
import com.yongoe.ecy.basic.controller.vo.res.DepartmentResVo;
import com.yongoe.ecy.basic.convert.DepartmentConvert;
import com.yongoe.ecy.basic.entity.Department;
import com.yongoe.ecy.basic.service.DepartmentService;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
    @Resource
    private DepartmentService departmentService;
    @Resource
    private UserService userService;
    @Resource
    private DepartmentConvert departmentConvert;

    @Operation(summary = "查询数据")
    @PostMapping("/tree")
    public R tree() {
        List<Department> list = departmentService.getMenuByTree(null);
        List<DepartmentResVo> voList = departmentConvert.entity2ResList(list);
        return R.success().put(voList);
    }

    @Operation(summary = "查询list")
    @PostMapping("/list")
    public R list() {
        List<Department> list = departmentService.getMenuByTree(new LambdaQueryWrapper<Department>()
                .eq(Department::getEnabled, true));
        List<DepartmentResVo> voList = departmentConvert.entity2ResList(list);
        return R.success().put(voList);
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody DepartmentReqVo reqVo) {
        Department department = departmentConvert.req2Entity(reqVo);
        departmentService.save(department);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody DepartmentReqVo reqVo) {
        Department department = departmentConvert.req2Entity(reqVo);
        departmentService.updateById(department);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        List<Department> list = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getParentId, id));
        if (!CollectionUtils.isEmpty(list)) {
            return R.error("请先清空子部门");
        }
        List<User> list1 = userService.list(new LambdaQueryWrapper<User>().eq(User::getDepartmentId, id));
        if (!CollectionUtils.isEmpty(list1)) {
            return R.error("请先清空部门下的用户");
        }
        departmentService.removeById(id);
        return R.success("删除成功");
    }


}