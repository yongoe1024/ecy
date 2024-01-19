package com.yongoe.ecy.system.controller;

import com.yongoe.ecy.config.aop.WebLog;
import com.yongoe.ecy.system.controller.vo.req.ForgetReq;
import com.yongoe.ecy.system.controller.vo.req.LoginReq;
import com.yongoe.ecy.system.controller.vo.req.RegisterReq;
import com.yongoe.ecy.system.service.LoginService;
import com.yongoe.ecy.utils.R;
import com.yongoe.ecy.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "系统登录")
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @WebLog
    @Operation(summary = "登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginReq loginReq, HttpServletRequest request) {
        return loginService.login(loginReq, request);
    }

    @WebLog
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public R logout() {
        UserUtils.logout();
        return R.success("退出登录");
    }

    @WebLog
    @Operation(summary = "注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterReq register) {
        return loginService.register(register);
    }

    @WebLog
    @Operation(summary = "找回密码")
    @PostMapping("/forget")
    public R forget(@RequestBody ForgetReq forget) {
        return loginService.forget(forget);
    }

}
