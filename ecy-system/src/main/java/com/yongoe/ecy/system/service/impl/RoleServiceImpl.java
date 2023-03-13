package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.RoleMenu;
import com.yongoe.ecy.system.mapper.RoleMapper;
import com.yongoe.ecy.system.mapper.RoleMenuMapper;
import com.yongoe.ecy.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Role> getList() {
        return baseMapper.getList();
    }

    @Override
    public void updateMenuRole(Long roleId, Long[] menuIds) {
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        if (menuIds != null && menuIds.length > 0) {
            for (Long menuId : menuIds) {
                roleMenuMapper.insert(new RoleMenu(roleId, menuId));
            }
        }
    }
}
