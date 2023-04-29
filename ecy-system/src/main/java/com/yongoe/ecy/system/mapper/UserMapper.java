package com.yongoe.ecy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    Page<User> getUserByPage(Page<User> page, @Param("user") User user);

    User getUserById(Long id);
}
