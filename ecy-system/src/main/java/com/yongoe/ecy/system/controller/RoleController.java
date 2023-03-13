package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.RoleMenu;
import com.yongoe.ecy.system.entity.UserRole;
import com.yongoe.ecy.system.mapper.RoleMenuMapper;
import com.yongoe.ecy.system.mapper.UserRoleMapper;
import com.yongoe.ecy.system.service.RoleService;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "角色")
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Operation(summary = "查询分页数据")
    @PostMapping("/list")
    public R list() {
        List<Role> list = roleService.getList();
        return R.success().put(list);
    }

    @Operation(summary = "根据角色id 修改菜单id")
    @PostMapping("/menu/update")
    public R updateMenuRole(Long roleId, Long[] menuIds) {
        roleService.updateMenuRole(roleId, menuIds);
        return R.success("修改成功");
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody Role role) {
        roleService.save(role);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody Role role) {
        roleService.updateById(role);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        if (id == 1) {
            return R.error("无法删除超级管理员角色");
        }
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", id));
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("role_id", id));
        roleService.removeById(id);
        return R.success("删除成功");
    }

}