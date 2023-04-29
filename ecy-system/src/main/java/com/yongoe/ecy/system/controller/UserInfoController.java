package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yongoe.ecy.system.controller.vo.req.UserReqVo;
import com.yongoe.ecy.system.controller.vo.res.MenuResVo;
import com.yongoe.ecy.system.controller.vo.res.UserInfoResVo;
import com.yongoe.ecy.system.convert.MenuConvert;
import com.yongoe.ecy.system.convert.UserConvert;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.MenuService;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.FileUtils;
import com.yongoe.ecy.utils.IpUtils;
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

    @Operation(summary = "查询个人信息")
    @PostMapping("/user/info")
    public R getUserInfo(HttpServletRequest request) {
        User user = UserUtils.getUser();
        user = userService.getUserById(user.getId());
        // 顺便修改登录时间
        user.setLastTime(LocalDateTime.now());
        user.setLastIp(IpUtils.getIp(request));
        userService.updateById(user);
        UserInfoResVo userInfo = userConvert.entity2UserInfo(user);
        return R.success().put(userInfo);
    }

    @Operation(summary = "查询个人菜单")
    @PostMapping("/user/menu")
    public R getMenuById() {
        List<Menu> list = menuService.getMenuByUser();
        List<MenuResVo> voList = menuConvert.entity2Res(list);
        return R.success().put(voList);
    }

    @Operation(summary = "修改个人信息")
    @PostMapping("/user/update")
    public R updateUserinfo(@RequestBody UserReqVo reqVo) {
        Long userId = UserUtils.getUserId();
        User user = userService.getUserById(userId);
        user.setPhone(reqVo.getPhone());
        user.setEmail(reqVo.getEmail());
        user.setRemark(reqVo.getRemark());
        userService.updateById(user);
        return R.success("修改成功");
    }

    @Operation(summary = "修改头像")
    @PostMapping("/user/avatar")
    public R uploadFile(MultipartFile file) {
        User user = UserUtils.getUser();
        String url = FileUtils.saveFile(file);
        userService.update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, user.getId())
                .set(User::getAvatar, url));
        return R.success("修改成功");
    }

    @Operation(summary = "修改密码")
    @PostMapping("/user/password")
    public R password(@RequestBody UserReqVo reqVo) {
        User user = UserUtils.getUser();
        String password = reqVo.getPassword();
        userService.update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, user.getId())
                .set(User::getPassword, password));
        return R.success("修改成功");

    }

}
