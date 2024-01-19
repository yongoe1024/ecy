package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.config.aop.WebLog;
import com.yongoe.ecy.system.controller.vo.excel.UserExcel;
import com.yongoe.ecy.system.controller.vo.req.UserReq;
import com.yongoe.ecy.system.controller.vo.res.UserRes;
import com.yongoe.ecy.system.convert.UserConvert;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.ExcelUtils;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    private UserConvert convert;

    //@WebLog
    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(@RequestBody UserReq req) {
        User entity = convert.req2Entity(req);
        Page<User> page = userService.getUserByPage(Page.of(req.getCurrent(), req.getSize()), entity);
        Page<UserRes> resPage = convert.entity2ResPage(page);
        return R.success().put(new PageUtils(resPage));
    }

    @WebLog
    @Operation(summary = "添加数据")
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

    @WebLog
    @Operation(summary = "重置密码")
    @PostMapping("/update/password")
    public R updatePassword(@RequestBody UserReq req) {
        Long id = req.getId();
        String password = req.getPassword();
        userService.update(new LambdaUpdateWrapper<User>().eq(User::getId, id).set(User::getPassword, password));
        return R.success("修改成功");
    }

    @WebLog
    @Operation(summary = "修改数据")
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

    @WebLog
    @Operation(summary = "删除数据")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        if (id == 1) {
            return R.error("无法删除超级管理员");
        }
        userService.deleteUser(id);
        return R.success("删除成功");
    }

    @WebLog
    @Operation(summary = "导入数据")
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        List<UserExcel> excelList = ExcelUtils.upload(file, UserExcel.class);
        userService.upload(excelList);
        return R.success("导入成功");
    }

    @WebLog
    @Operation(summary = "导出模板")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        ExcelUtils.export(response, new ArrayList<>(), UserExcel.class);
    }
}