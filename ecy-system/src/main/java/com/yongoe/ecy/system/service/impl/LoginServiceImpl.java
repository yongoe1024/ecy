package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.entity.UserAuths;
import com.yongoe.ecy.system.mapper.LoginMapper;
import com.yongoe.ecy.system.mapper.UserAuthsMapper;
import com.yongoe.ecy.system.service.LoginService;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.JwtUtils;
import com.yongoe.ecy.utils.R;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 登录
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthsMapper userAuthsMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R login(User loginParam, HttpServletRequest request) {
        String identifier = loginParam.getIdentifier();
        String credential = loginParam.getCredential();
//        HttpSession session = request.getSession();
//        String captcha = (String) session.getAttribute("captcha");
//        request.getSession().removeAttribute("captcha");
//        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)) {
//            return R.error(402, "验证码错误");
//        }
        User user = loginMapper.getUserByIdentifier(identifier);
        if (user == null)
            return R.error("账号不存在");
        if (!credential.equals(user.getCredential()))
            return R.error("密码错误");
        if (!user.getEnabled()) {
            return R.error(401, "账号被禁用");
        }
        // 生成token
        String token = JwtUtils.createToken(user.getId().toString());
        return R.success("登录成功").put(token);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public R register(User user) {
        try {
            user.setAvatar("https://gulimall-mrguo.oss-cn-beijing.aliyuncs.com/retouch_2022082218101242.jpg");
            user.setEnabled(true);
            userService.save(user);
            UserAuths userAuths = new UserAuths(
                    user.getId(),
                    "username",
                    user.getIdentifier(),
                    user.getCredential());
            userAuthsMapper.insert(userAuths);
            String token = JwtUtils.createToken(user.getId().toString());
            return R.success("注册成功").put(token);
        } catch (Exception e) {
            return R.error("账号重复");
        }
    }

    @Override
    public R forget(Map<String, String> map) {
        String email = map.get("email");
        String code = map.get("code");
        String s = redisTemplate.opsForValue().get(email);
        if (!code.equals(s))
            return R.error("验证码错误");
        String identifier = map.get("identifier");
        String credential = map.get("credential");
        UserAuths userAuths = userAuthsMapper.selectOne(new QueryWrapper<UserAuths>().eq("identity_type", "username").eq("identifier", identifier));
        Long userId = userAuths.getUserId();
        User userById = userService.getUserById(userId);
        if (userById.getEmail().equals(email))
            return R.error("邮箱错误");
        userAuthsMapper.update(null, new UpdateWrapper<UserAuths>()
                .eq("user_id", userAuths.getUserId())
                .set("credential", credential));
        return R.success("修改成功，即将跳转登录页面");
    }

}
