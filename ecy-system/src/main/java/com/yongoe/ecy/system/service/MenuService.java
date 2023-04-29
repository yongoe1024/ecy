package com.yongoe.ecy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.system.entity.Menu;

import java.util.List;

/**
 * 菜单
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface MenuService extends IService<Menu> {
    /**
     * 得到树形菜单
     */
    List<Menu> getMenuByTree();

    /**
     * 根据菜单id，得到角色list
     */
    List<Long> getRoleIdsByMenuId(Long menuId);

    /**
     * 用户的菜单
     */
    List<Menu> getMenuByUser();
}
