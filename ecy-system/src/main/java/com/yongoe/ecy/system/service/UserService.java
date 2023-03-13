package com.yongoe.ecy.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.system.entity.User;

import java.util.List;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface UserService extends IService<User> {

    Page<User> getUserByPage(Page<User> page, User user);

    /**
     * 根据姓名查用户
     */
    User getUserById(Long id);

    /**
     * 更新用户的角色
     */
    void updateUserRole(Long userId, List<Long> roleIds);

}
