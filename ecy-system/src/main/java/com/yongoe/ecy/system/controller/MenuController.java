package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yongoe.ecy.config.aop.WebLog;
import com.yongoe.ecy.system.controller.vo.req.MenuReq;
import com.yongoe.ecy.system.controller.vo.res.MenuRes;
import com.yongoe.ecy.system.convert.MenuConvert;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.RoleMenu;
import com.yongoe.ecy.system.mapper.RoleMenuMapper;
import com.yongoe.ecy.system.service.MenuService;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "菜单")
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuConvert convert;

    //@WebLog
    @Operation(summary = "查询数据")
    @PostMapping("/tree")
    public R tree() {
        List<Menu> list = menuService.getMenuByTree();
        List<MenuRes> resList = convert.entity2Res(list);
        return R.success().put(resList);
    }

    @WebLog
    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody MenuReq req) {
        Menu menu = convert.req2Entity(req);
        menuService.save(menu);
        return R.success("添加成功");
    }

    @WebLog
    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody MenuReq req) {
        Menu menu = convert.req2Entity(req);
        menuService.updateById(menu);
        return R.success("修改成功");
    }

    @WebLog
    @Operation(summary = "删除数据")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        List<Menu> list = menuService.list(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, id));
        if (!CollectionUtils.isEmpty(list)) {
            return R.error("请先清空子目录");
        }
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, id));
        menuService.removeById(id);
        return R.success("删除成功");
    }


}