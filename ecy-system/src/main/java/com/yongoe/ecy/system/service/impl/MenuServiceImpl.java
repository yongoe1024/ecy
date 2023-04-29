package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.User;
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
        User user = UserUtils.getUser();
        List<Menu> menuByUser = getMenuByUserCache(user.getId());
        return tree(0L, menuByUser);
    }

    public List<Menu> getMenuByUserCache(Long id) {
        User user = UserUtils.getUser();
        // 超级管理员
        for (Role role : user.getRoleList()) {
            if (role.getId() == 1) {
                return baseMapper.selectList(new LambdaQueryWrapper<Menu>()
                        .eq(Menu::getEnabled, true)
                        .orderByDesc(Menu::getSort));
            }
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
