package com.yongoe.ecy.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yongoe.ecy.config.security.UserThreadLocal;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.LoginService;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "登录")
@ApiSupport(order = 999)
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public R login(@RequestBody User user, HttpServletRequest request) {
        return loginService.login(user, request);
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public R logout() {
        UserThreadLocal.remove();
        return R.success("退出登录");
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public R register(@RequestBody User user) {
        return loginService.register(user);
    }

    @Operation(summary = "找回密码")
    @PostMapping("/forget")
    public R forget(@RequestBody Map<String, String> map) {
        return loginService.forget(map);
    }

}
