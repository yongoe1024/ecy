package com.yongoe.ecy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.Role;

import java.util.List;

/**
 * 菜单
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface MenuService extends IService<Menu> {


    List<Menu> getTree();

    /**
     * 根据菜单id，得到角色list
     */
    List<Role> getRolesByMenuId(Long id);

    /**
     * 用户 所属的菜单
     */
    List<Menu> getMenuByUser();

}
