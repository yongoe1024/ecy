package com.yongoe.ecy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取角色list 包含菜单id数组
     */
    Page<Role> getRoleByPage(Page<Role> page, @Param("role") Role role);
}
