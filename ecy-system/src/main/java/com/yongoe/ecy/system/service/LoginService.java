package com.yongoe.ecy.system.service;

import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.utils.R;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 登录
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface LoginService {

    /**
     * 登陆之后返回token
     */
    R login(User loginParam, HttpServletRequest request);

    /**
     * 注册之后返回token
     */
    R register(User user);

    /**
     * 发送邮件找回密码
     */
    R forget(Map<String, String> map);
}
