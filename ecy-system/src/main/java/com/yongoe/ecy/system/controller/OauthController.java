package com.yongoe.ecy.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yongoe.ecy.system.service.OauthService;
import com.yongoe.ecy.utils.R;
import com.yongoe.ecy.utils.WXH5SignatureUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
    private OauthService oauthService;
    @Resource
    private WXH5SignatureUtil wxh5SignatureUtil;

    @GetMapping("/oauth/qq/redirect")
    public void redirectQQ(HttpServletResponse response) throws IOException {
        AuthRequest qqAuthRequest = oauthService.getQQAuthRequest();
        if (qqAuthRequest != null)
            response.sendRedirect(qqAuthRequest.authorize(AuthStateUtils.createState()));
        else {
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.getWriter().println("QQ登录配置错误");
        }
    }

    @GetMapping("/oauth/wxh5/redirect")
    public void redirectWXH5(HttpServletResponse response) throws IOException {
        response.sendRedirect(oauthService.getWXH5Redirect());
    }

    @PostMapping("/oauth/wxh5/config")
    public R config(String url) throws UnsupportedEncodingException {
        try {
            String decodedURL = URLDecoder.decode(url, StandardCharsets.UTF_8);
            Map<String, String> returnMap = wxh5SignatureUtil.getWechatSignature(decodedURL);
            return R.success().put(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取微信配置失败");
        }
    }

    @GetMapping("/oauth/wxh5/callback")
    public R callbackWXH5(String code) throws JsonProcessingException {
        return oauthService.callbackWXH5(code);
    }

    @GetMapping("/oauth/qq/callback")
    public R callbackQQ(AuthCallback callback) throws JsonProcessingException {
        return oauthService.callbackQQ(callback);
    }


}


