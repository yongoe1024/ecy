package com.yongoe.ecy.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.system.controller.vo.excel.UserExcel;
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

    void updateUserRole(Long userId, List<Long> roleIds);

    User getUserById(Long id);

    void upload(List<UserExcel> excelList);

    void deleteUser(Long id);
}
