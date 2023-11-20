package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.req.UserReq;
import com.yongoe.ecy.system.controller.vo.res.UserRes;
import com.yongoe.ecy.system.convert.UserConvert;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.entity.UserAuths;
import com.yongoe.ecy.system.entity.UserRole;
import com.yongoe.ecy.system.mapper.UserAuthsMapper;
import com.yongoe.ecy.system.mapper.UserRoleMapper;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "用户")
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserAuthsMapper userAuthsMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserConvert convert;

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size, @RequestBody UserReq req) {
        User entity = convert.req2Entity(req);
        Page<User> page = userService.getUserByPage(Page.of(current, size), entity);
        Page<UserRes> resPage = convert.entity2ResPage(page);
        return R.success().put(new PageUtils(resPage));
    }

    @Operation(summary = "添加数据")
    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/add")
    public R add(@RequestBody UserReq req) {
        User user = convert.req2Entity(req);
        user.setAvatar("/");
        try {
            userService.save(user);
            userService.updateUserRole(user.getId(), req.getRoleIds());
            return R.success("添加成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("账号重复");
        }
    }

    @Operation(summary = "重置密码")
    @PostMapping("/update/password")
    public R updatePassword(@RequestBody UserReq req) {
        Long id = req.getId();
        String password = req.getPassword();
        userService.update(new LambdaUpdateWrapper<User>().eq(User::getId, id).set(User::getPassword, password));
        return R.success("修改成功");
    }

    @Operation(summary = "修改数据")
    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/update")
    public R update(@RequestBody UserReq req) {
        User user = convert.req2Entity(req);
        try {
            userService.updateById(user);
            userService.updateUserRole(user.getId(), req.getRoleIds());
            return R.success("修改成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("账号重复");
        }
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        if (id == 1) {
            return R.error("无法删除超级管理员");
        }
        userAuthsMapper.delete(new LambdaQueryWrapper<UserAuths>().eq(UserAuths::getUserId, id));
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
        userService.removeById(id);
        return R.success("删除成功");
    }

}