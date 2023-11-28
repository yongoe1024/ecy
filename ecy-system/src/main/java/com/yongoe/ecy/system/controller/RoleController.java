package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.req.RoleReq;
import com.yongoe.ecy.system.controller.vo.res.RoleRes;
import com.yongoe.ecy.system.convert.RoleConvert;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.RoleMenu;
import com.yongoe.ecy.system.entity.UserRole;
import com.yongoe.ecy.system.mapper.RoleMenuMapper;
import com.yongoe.ecy.system.mapper.UserRoleMapper;
import com.yongoe.ecy.system.service.RoleService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
    @Resource
    private RoleService roleService;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleConvert convert;

    @Operation(summary = "查询数据")
    @PostMapping("/list")
    public R list() {
        Page<Role> list = roleService.getRoleByPage(Page.of(-1, -1), new Role());
        List<RoleRes> resList = convert.entity2Res(list.getRecords());
        return R.success().put(resList);
    }

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R list(Long current, Long size, @RequestBody RoleReq roleReq) {
        Role role = convert.req2Entity(roleReq);
        Page<Role> list = roleService.getRoleByPage(Page.of(current, size), role);
        Page<RoleRes> roleResPage = convert.entity2ResPage(list);
        return R.success().put(new PageUtils(roleResPage));
    }

    @Operation(summary = "根据角色id 修改菜单id")
    @PostMapping("/menu/update")
    public R updateMenuRole(Long roleId, Long[] menuIds) {
        roleService.updateMenuRole(roleId, menuIds);
        return R.success("修改成功");
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody RoleReq req) {
        Role role = convert.req2Entity(req);
        roleService.save(role);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody RoleReq req) {
        Role role = convert.req2Entity(req);
        roleService.updateById(role);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        if (id == 1 || id == 2) {
            return R.error("禁止删除");
        }
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, id));
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, id));
        roleService.removeById(id);
        return R.success("删除成功");
    }

}