package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.RoleMenu;
import com.yongoe.ecy.system.mapper.RoleMenuMapper;
import com.yongoe.ecy.system.service.MenuService;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "菜单")
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Operation(summary = "查询当前用户菜单")
    @PostMapping("/user/menu")
    public R getMenuById() {
        return R.success().put(menuService.getMenuByUser());
    }

    @Operation(summary = "查询数据")
    @PostMapping("/system/menu/tree")
    public R tree() {
        return R.success().put(menuService.getTree());
    }

    @Operation(summary = "添加数据")
    @PostMapping("/system/menu/add")
    public R add(@RequestBody Menu menu) {
        menuService.save(menu);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/system/menu/update")
    public R update(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/system/menu/delete/{id}")
    public R delete(@PathVariable Long id) {
        List<Menu> list = menuService.list(new QueryWrapper<Menu>().eq("parent_id", id));
        if (!CollectionUtils.isEmpty(list)) {
            return R.error("请先清空子目录");
        }
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", id));
        menuService.removeById(id);
        return R.success("删除成功");
    }
}