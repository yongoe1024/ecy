package com.yongoe.ecy.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yongoe.ecy.system.controller.vo.req.QQReq;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.entity.UserAuths;
import com.yongoe.ecy.system.mapper.UserAuthsMapper;
import com.yongoe.ecy.system.service.OauthService;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.JwtUtils;
import com.yongoe.ecy.utils.R;
import com.yongoe.ecy.utils.SysConfigUtils;
import jakarta.annotation.Resource;
import me.zhyd.oauth.cache.AuthCacheConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * OauthService实现类
 *
 * @author yongoe
 * @since 2023/11/6
 */
@Service
public class OauthServiceImpl implements OauthService {
    @Resource
    private UserAuthsMapper userAuthsMapper;
    @Resource
    private UserService userService;
    @Resource
    private SysConfigUtils configUtils;

    public String getWXH5Redirect() {
        String clientId = configUtils.get("wxh5-clientId");
        String redirectUri = configUtils.get("wxh5-redirectUri");
        String decodedURL = URLDecoder.decode(redirectUri, StandardCharsets.UTF_8);
        return "https://open.weixin.qq.com/connect/qrconnect?appid=" + clientId + "&redirect_uri=" + decodedURL
                + "&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
    }

    public AuthRequest getQQAuthRequest() {
        try {
            AuthCacheConfig.timeout = 10 * 60 * 1000;
            String clientId = configUtils.get("qq-clientId");
            String clientSecret = configUtils.get("qq-clientSecret");
            String redirectUri = configUtils.get("qq-redirectUri");
            return new AuthQqRequest(AuthConfig.builder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .redirectUri(redirectUri)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public R callbackWXH5(String code) {
        String clientId = configUtils.get("wxh5-clientId");
        String clientSecret = configUtils.get("wxh5-clientSecret");
        if (StringUtils.isEmpty(code)) {
            return R.error("code为空");
        }
        String url = "https://api.weixin.qq.com/sns/oauth2/" +
                "access_token?appid=" + clientId + "&secret=" + clientSecret + "&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String openId = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");
        if (StringUtils.isEmpty(openId)) {
            return R.error("openid获取失败");
        }
        UserAuths userAuths = userAuthsMapper.selectOne(new LambdaQueryWrapper<UserAuths>()
                .eq(UserAuths::getOpenid, openId));
        if (userAuths != null) {
            Long userId = userAuths.getUserId();
            String token = JwtUtils.createToken(userId.toString());
            return R.success().put(token);
        }
        // 获取新用户信息
        if (StringUtils.isEmpty(access_token)) {
            return R.error("access_token获取失败");
        }
        url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token + "&openid=" + openId + "&lang=zh_CN";
        result = restTemplate.getForObject(url, String.class);
        jsonObject = JSONObject.parseObject(result);
        String nickname = jsonObject.getString("nickname");
        String headimgurl = jsonObject.getString("headimgurl");
        nickname = new String(nickname.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Long userId = createUser(nickname, headimgurl, openId, access_token, "wxh5");
        String token = JwtUtils.createToken(userId.toString());
        return R.success().put(token);
    }

    public R callbackQQ(AuthCallback callback) throws JsonProcessingException {
        AuthResponse login = getQQAuthRequest().login(callback);
        if (login!=null && login.getCode() != 2000)
            return R.error("qq登录失败");
        Object data = login.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(data);
        //序列化的时候序列对象的所有属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        QQReq qq = objectMapper.readValue(res, QQReq.class);
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
            Long userId = createUser(nickname, figureurl_1, openId, accessToken, "qq");
            String token = JwtUtils.createToken(userId.toString());
            return R.success().put(token);
        }
    }

    private Long createUser(String name, String avatar, String openid, String accessToken, String loginType) {
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
                .loginType(loginType)
                .userId(userId)
                .openid(openid)
                .accessToken(accessToken).build();
        userAuthsMapper.insert(userAuths);
        return userId;
    }
}
