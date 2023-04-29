package com.yongoe.ecy.system.service;

import com.yongoe.ecy.system.controller.vo.req.ForgetVO;
import com.yongoe.ecy.system.controller.vo.req.LoginVo;
import com.yongoe.ecy.system.controller.vo.req.RegisterVo;
import com.yongoe.ecy.utils.R;
import jakarta.servlet.http.HttpServletRequest;

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
    R login(LoginVo loginVo, HttpServletRequest request);

    /**
     * 注册之后返回token
     */
    R register(RegisterVo register);

    /**
     * 找回密码
     */
    R forget(ForgetVO forget);
}
