package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yongoe.ecy.config.aop.WebLog;
import com.yongoe.ecy.system.controller.vo.req.UserReq;
import com.yongoe.ecy.system.controller.vo.res.MenuRes;
import com.yongoe.ecy.system.controller.vo.res.UserInfoRes;
import com.yongoe.ecy.system.convert.MenuConvert;
import com.yongoe.ecy.system.convert.UserConvert;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.MenuService;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.FileUtils;
import com.yongoe.ecy.utils.IPUtils;
import com.yongoe.ecy.utils.R;
import com.yongoe.ecy.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 个人信息
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "个人信息")
@RestController
public class UserInfoController {
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @Resource
    private UserConvert userConvert;
    @Resource
    private MenuConvert menuConvert;

    //@WebLog
    @Operation(summary = "查询个人信息")
    @PostMapping("/user/info")
    public R getUserInfo(HttpServletRequest request) {
        User user = userService.getUserById(UserUtils.getUserId());
        // 顺便修改登录时间
        user.setLastTime(LocalDateTime.now());
        user.setLastIp(IPUtils.getIp(request));
        userService.updateById(user);
        UserInfoRes userInfo = userConvert.entity2UserInfo(user);
        return R.success().put(userInfo);
    }

    //@WebLog
    @Operation(summary = "查询个人菜单")
    @PostMapping("/user/menu")
    public R getMenuById() {
        List<Menu> list = menuService.getMenuByUser();
        List<MenuRes> resList = menuConvert.entity2Res(list);
        return R.success().put(resList);
    }

    @WebLog
    @Operation(summary = "修改个人信息")
    @PostMapping("/user/update")
    public R updateUserinfo(@RequestBody UserReq req) {
        Long userId = UserUtils.getUserId();
        User user = userService.getUserById(userId);
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setRemark(req.getRemark());
        userService.updateById(user);
        return R.success("修改成功");
    }

    @WebLog
    @Operation(summary = "修改头像")
    @PostMapping("/user/avatar")
    public R uploadFile(MultipartFile file) {
        String url = FileUtils.saveFile(file);
        userService.update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, UserUtils.getUserId())
                .set(User::getAvatar, url));
        return R.success("修改成功");
    }

    @WebLog
    @Operation(summary = "修改密码")
    @PostMapping("/user/password")
    public R password(@RequestBody UserReq req) {
        String password = req.getPassword();
        userService.update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, UserUtils.getUserId())
                .set(User::getPassword, password));
        return R.success("修改成功");

    }

}
