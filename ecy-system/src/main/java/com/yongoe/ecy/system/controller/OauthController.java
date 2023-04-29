package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yongoe.ecy.system.controller.vo.req.QqReqVo;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.entity.UserAuths;
import com.yongoe.ecy.system.mapper.UserAuthsMapper;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.JwtUtils;
import com.yongoe.ecy.utils.R;
import com.yongoe.ecy.utils.SysConfigUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.cache.AuthCacheConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 第三方登录
 *
 * @author yongoe
 * @since 2023/3/31
 */
@Tag(name = "第三方登录")
@RestController
public class OauthController {
    @Resource
    private UserAuthsMapper userAuthsMapper;
    @Resource
    private UserService userService;
    @Resource
    private SysConfigUtils configUtils;

    @GetMapping("/oauth/qq/redirect")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(getAuthRequest().authorize(AuthStateUtils.createState()));
    }

    @GetMapping("/oauth/qq/callback")
    @Transactional(rollbackFor = RuntimeException.class)
    public R callback(AuthCallback callback) throws JsonProcessingException {
        AuthResponse login = getAuthRequest().login(callback);
        if (login.getCode() != 2000)
            return R.error("qq登录失败");
        Object data = login.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(data);
        //序列化的时候序列对象的所有属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        QqReqVo qq = objectMapper.readValue(res, QqReqVo.class);
        // 获取信息
        String accessToken = qq.getToken().getAccessToken();
        String openId = qq.getToken().getOpenId();
        String nickname = qq.getNickname();
        String figureurl_1 = qq.getRawUserInfo().getFigureurl_1();
        UserAuths userAuths = userAuthsMapper.selectOne(new LambdaQueryWrapper<UserAuths>()
                .eq(UserAuths::getOpenid, openId));
        if (userAuths != null) {
            Long userId = userAuths.getUserId();
            String token = JwtUtils.createToken(userId.toString());
            return R.success().put(token);
        } else {
            Long userId = createUser(nickname, figureurl_1, openId, accessToken);
            String token = JwtUtils.createToken(userId.toString());
            return R.success().put(token);
        }
    }

    public Long createUser(String name, String avatar, String openid, String accessToken) {
        User build = User.builder().phone("")
                .email("")
                .name(name)
                .avatar(avatar)
                .enabled(true)
                .username(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).build();
        userService.save(build);
        Long userId = build.getId();
        // 新用户默认2
        userService.updateUserRole(userId, List.of(2L));
        UserAuths userAuths = UserAuths.builder()
                .loginType("qq")
                .userId(userId)
                .openid(openid)
                .accessToken(accessToken).build();
        userAuthsMapper.insert(userAuths);
        return userId;
    }


    public AuthRequest getAuthRequest() {
        AuthCacheConfig.timeout = 10 * 60 * 1000;
        String clientId = configUtils.get("qq-clientId");
        String clientSecret = configUtils.get("qq-clientSecret");
        String redirectUri = configUtils.get("qq-redirectUri");
        return new AuthQqRequest(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build());
    }


}


