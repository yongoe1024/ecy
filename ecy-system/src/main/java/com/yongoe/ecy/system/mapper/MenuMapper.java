package com.yongoe.ecy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取菜单对应角色
     */
    List<Role> getRolesByMenuId(Long id);

    /**
     * 用户 所属的菜单
     */
    List<Menu> getMenuByUser(Long id);
}
