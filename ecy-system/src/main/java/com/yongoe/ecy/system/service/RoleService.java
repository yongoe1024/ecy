package com.yongoe.ecy.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.system.entity.Role;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取角色列表，包含菜单id
     */
    Page<Role> getRoleByPage(Page<Role> page, Role role);

    /**
     * 更新用户-角色
     */
    void updateMenuRole(Long roleId, Long[] menuIds);

    void deleteRole(Long id);
}
