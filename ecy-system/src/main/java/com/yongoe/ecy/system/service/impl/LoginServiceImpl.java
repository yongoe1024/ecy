package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yongoe.ecy.system.controller.vo.req.ForgetReq;
import com.yongoe.ecy.system.controller.vo.req.LoginReq;
import com.yongoe.ecy.system.controller.vo.req.RegisterReq;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.LoginService;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.JwtUtils;
import com.yongoe.ecy.utils.R;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * 登录
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserService userService;

    @Override
    public R login(LoginReq loginReq, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        String captcha = (String) session.getAttribute("captcha");
//        request.getSession().removeAttribute("captcha");
//        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)) {
//            return R.error(402, "验证码错误");
//        }
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginReq.getUsername()));
        if (user == null)
            return R.error("账号或密码错误");
        if (!loginReq.getPassword().equals(user.getPassword()))
            return R.error("账号或密码错误");
        if (!user.getEnabled()) {
            return R.error("账号被禁用");
        }
        // 生成token
        String token = JwtUtils.createToken(user.getId().toString());
        return R.success("登录成功").put(token);
    }

    @Override
    public R register(RegisterReq register) {
        try {
            User user = User.builder().avatar("/")
                    .enabled(true)
                    .username(register.getUsername())
                    .password(register.getPassword())
                    .name(register.getName())
                    .email(register.getEmail())
                    .phone(register.getPhone()).build();
            userService.save(user);
            // 默认角色为新注册
            List<Long> list = List.of(2L);
            userService.updateUserRole(user.getId(), list);
            String token = JwtUtils.createToken(user.getId().toString());
            return R.success("注册成功").put(token);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("账号重复");
        }
    }

    @Override
    public R forget(ForgetReq forget) {
        User one = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, forget.getUsername())
                .eq(User::getEmail, forget.getEmail()));
        one.setPassword(forget.getPassword());
        userService.updateById(one);
        return R.success("修改成功，请重新登录");
    }

}
