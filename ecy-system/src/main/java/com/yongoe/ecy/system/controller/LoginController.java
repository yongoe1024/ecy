package com.yongoe.ecy.system.controller;

import com.yongoe.ecy.system.controller.vo.req.ForgetVO;
import com.yongoe.ecy.system.controller.vo.req.LoginVo;
import com.yongoe.ecy.system.controller.vo.req.RegisterVo;
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
@Tag(name = "登录")
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        return loginService.login(loginVo, request);
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public R logout() {
        UserUtils.logout();
        return R.success("退出登录");
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo register) {
        return loginService.register(register);
    }

    @Operation(summary = "找回密码")
    @PostMapping("/forget")
    public R forget(@RequestBody ForgetVO forget) {
        return loginService.forget(forget);
    }

}
