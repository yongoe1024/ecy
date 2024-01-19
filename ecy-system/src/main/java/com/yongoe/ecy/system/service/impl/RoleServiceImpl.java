package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.RoleMenu;
import com.yongoe.ecy.system.entity.UserRole;
import com.yongoe.ecy.system.mapper.RoleMapper;
import com.yongoe.ecy.system.mapper.RoleMenuMapper;
import com.yongoe.ecy.system.mapper.UserRoleMapper;
import com.yongoe.ecy.system.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public Page<Role> getRoleByPage(Page<Role> page, Role role) {
        return baseMapper.getRoleByPage(page, role);
    }

    @Override
    public void updateMenuRole(Long roleId, Long[] menuIds) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        if (menuIds != null && menuIds.length > 0) {
            for (Long menuId : menuIds) {
                roleMenuMapper.insert(new RoleMenu(roleId, menuId));
            }
        }
    }

    @Override
    public void deleteRole(Long id) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, id));
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, id));
        this.removeById(id);
    }
}
