package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.config.security.UserThreadLocal;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.mapper.MenuMapper;
import com.yongoe.ecy.system.service.MenuService;
import org.springframework.stereotype.Service;

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
    public List<Menu> getTree() {
        List<Menu> list = baseMapper.selectList(new QueryWrapper<Menu>().orderByDesc("sort"));
        return tree(0L, list);
    }

    @Override
    public List<Menu> getMenuByUser() {
        User user = UserThreadLocal.get();
        // 超级管理员
        for (Role role : user.getRoleList()) {
            if (role.getId() == 1) {
                List<Menu> list = baseMapper.selectList(new QueryWrapper<Menu>()
                        .eq("enabled", true).orderByDesc("sort"));
                return tree(0L, list);
            }
        }
        List<Menu> menuByUser = baseMapper.getMenuByUser(user.getId());
        return tree(0L, menuByUser);
    }

    @Override
    public List<Role> getRolesByMenuId(Long id) {
        List<Role> rolesByMenuId = baseMapper.getRolesByMenuId(id);
        return rolesByMenuId == null ? new ArrayList<Role>() : rolesByMenuId;
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
