package com.yongoe.ecy.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yongoe.ecy.utils.R;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;

/**
 * Oauth service
 *
 * @author yongoe
 * @since 2023/11/6
 */
public interface OauthService {

    /**
     * 获取微信H5登录重定向地址
     */
    String getWXH5Redirect();

    /**
     * 微信H5登录回调
     */
    R callbackWXH5(String code) throws JsonProcessingException;

    /**
     * 获取QQ登录重定向地址
     */
    AuthRequest getQQAuthRequest();

    /**
     * QQ登录回调
     */
    R callbackQQ(AuthCallback callback) throws JsonProcessingException;
}
