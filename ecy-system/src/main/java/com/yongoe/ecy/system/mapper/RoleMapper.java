package com.yongoe.ecy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yongoe.ecy.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 角色list 包含菜单id
     */
    List<Role> getList();
}
