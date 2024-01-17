package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.mapper.MenuMapper;
import com.yongoe.ecy.system.service.MenuService;
import com.yongoe.ecy.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getMenuByTree() {
        List<Menu> list = baseMapper.selectList(new LambdaQueryWrapper<Menu>().orderByDesc(Menu::getSort));
        return tree(0L, list);
    }

    @Override
    public List<Long> getRoleIdsByMenuId(Long menuId) {
        List<Long> roleIds = baseMapper.getRoleIdsByMenuId(menuId);
        return CollectionUtils.isEmpty(roleIds) ? new ArrayList<>() : roleIds;
    }


    @Override
    public List<Menu> getMenuByUser() {
        List<Menu> menuByUser = getMenuByUser(UserUtils.getUserId());
        return tree(0L, menuByUser);
    }

    private List<Menu> getMenuByUser(Long id) {
        Boolean admin = UserUtils.isRole("admin");
        // 超级管理员
        if (admin) {
            return baseMapper.selectList(new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getEnabled, true)
                    .orderByDesc(Menu::getSort));
        }
        return baseMapper.getMenuByUser(id);
    }


    private List<Menu> tree(Long rootId, List<Menu> all) {
        return all.stream()
                .filter(entity -> entity.getParentId().longValue() == rootId.longValue())
                .peek(entity -> {
                    List<Menu> ch = tree(entity.getId(), all);
                    entity.setChildren(ch);
                })
                .collect(Collectors.toList());
    }

}
