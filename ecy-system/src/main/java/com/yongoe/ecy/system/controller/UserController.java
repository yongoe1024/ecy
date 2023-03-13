package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.config.security.UserThreadLocal;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.entity.UserAuths;
import com.yongoe.ecy.system.entity.UserRole;
import com.yongoe.ecy.system.mapper.UserAuthsMapper;
import com.yongoe.ecy.system.mapper.UserRoleMapper;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.FileUtils;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "用户")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthsMapper userAuthsMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Operation(summary = "用户信息")
    @PostMapping("/user/info")
    public R getUserInfo() {
        User user = UserThreadLocal.get();
        User userById = userService.getUserById(user.getId());
        // 顺便修改登录时间
        userById.setLastTime(LocalDateTime.now());
        userService.updateById(userById);
        return R.success().put(userById);
    }

    @Operation(summary = "用户修改个人信息")
    @PostMapping("/user/update")
    @Transactional(rollbackFor = RuntimeException.class)
    public R updateUserinfo(@RequestBody User user) {
        try {
            User userById = userService.getUserById(user.getId());
            userById.setName(user.getName());
            userById.setPhone(user.getPhone());
            userById.setRemark(user.getRemark());
            userService.updateById(userById);
            userAuthsMapper.update(null, new UpdateWrapper<UserAuths>()
                    .eq("user_id", user.getId()).eq("identity_type", "username")
                    .set("identifier", user.getIdentifier()));
            return R.success("修改成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("账号重复");
        }
    }

    @Operation(summary = "用户修改头像")
    @PostMapping("/user/avatar")
    public R uploadFile(MultipartFile file, HttpServletRequest request) {
        User user = UserThreadLocal.get();
        String url = FileUtils.getFileDirectory(file, request);
        userService.update(new UpdateWrapper<User>().eq("id", user.getId()).set("avatar", url));
        return R.success("修改成功");
    }

    @Operation(summary = "用户修改密码")
    @PostMapping("/user/password")
    public R password(@RequestBody User user) {
        userAuthsMapper.update(null, new UpdateWrapper<UserAuths>()
                .eq("user_id", user.getId())
                .set("credential", user.getCredential()));
        return R.success("修改成功");

    }

    @Operation(summary = "查询分页数据")
    @PostMapping("/system/user/page")
    public R page(Long current, Long size, @RequestBody User user) {
        Page<User> page = userService.getUserByPage(Page.of(current, size), user);
        return R.success().put(new PageUtils(page));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/system/user/add")
    @Transactional(rollbackFor = RuntimeException.class)
    public R add(@RequestBody User user) {
        try {
            userService.save(user);
            UserAuths username = new UserAuths(user.getId(), "username", user.getName(), "123456");
            userService.updateUserRole(user.getId(), user.getRoleIds());
            userAuthsMapper.insert(username);
            return R.success("添加成功,账号为 " + user.getName() + "，密码为 " + "123456");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("账号重复");
        }
    }

    @Operation(summary = "修改密码")
    @PostMapping("/system/user/update/password")
    public R updatePassword(@RequestBody User user) {
        // 修改所有账号的密码
        userAuthsMapper.update(null, new UpdateWrapper<UserAuths>()
                .eq("user_id", user.getId())
                .set("credential", user.getCredential()));
        return R.success("修改成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/system/user/update")
    @Transactional(rollbackFor = RuntimeException.class)
    public R update(@RequestBody User user) {
        try {
            userService.updateById(user);
            userService.updateUserRole(user.getId(), user.getRoleIds());
            //更新账号
            userAuthsMapper.update(null, new UpdateWrapper<UserAuths>()
                    .eq("user_id", user.getId()).eq("identity_type", "username")
                    .set("identifier", user.getIdentifier()));
            return R.success("修改成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("账号重复");
        }
    }

    @Operation(summary = "删除数据")
    @PostMapping("/system/user/delete/{id}")
    public R delete(@PathVariable Long id) {
        if (id == 1) {
            return R.error("无法删除超级管理员");
        }
        userAuthsMapper.delete(new QueryWrapper<UserAuths>().eq("user_id", id));
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", id));
        userService.removeById(id);
        return R.success("删除成功");
    }

}