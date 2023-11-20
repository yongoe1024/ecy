package com.yongoe.ecy.utils;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 微信H5获取配置工具
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class WXH5SignatureUtil {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SysConfigUtils configUtils;

    /**
     * 获取签名
     * 注意 URL 一定要动态获取，不能 hardcode
     */
    public Map<String, String> getWechatSignature(String url) {
        System.out.println(url);
        String token = getToken();
        String ticket = getTicket(token);
        String nonceStr = UUID.randomUUID().toString();
        // 时间戳
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        //注意这里参数名必须全部小写，且必须有序
        String context = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        //加密
        String signature = SHA1(context);
        //封装
        Map<String, String> result = new HashMap<>();
        result.put("jsapi_ticket", ticket);
        result.put("nonceStr", nonceStr);
        result.put("timestamp", timestamp);
        result.put("signature", signature);
        return result;
    }

    /**
     * 获取 token
     */
    private String getToken() {
        String clientId = configUtils.get("qq-clientId");
        String clientSecret = configUtils.get("qq-clientSecret");
        String cache = (String) redisUtils.get("access_token");
        if (!StringUtils.isEmpty(cache)) {
            return cache;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + clientId + "&secret=" + clientSecret;
        //请求链接获取token
        JSONObject jsonObject = httpGetForJson(url);
        String access_token = jsonObject.getString("access_token");
        redisUtils.set("access_token", access_token, 7000, TimeUnit.SECONDS);
        return access_token;
    }

    /**
     * 获取 ticket
     */
    private String getTicket(String token) {
        String cache = (String) redisUtils.get("ticket");
        if (!StringUtils.isEmpty(cache)) {
            return cache;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        //请求链接获取token
        JSONObject response = httpGetForJson(url);
        String ticket = response.getString("ticket");
        redisUtils.set("ticket", ticket, 7000, TimeUnit.SECONDS);
        return ticket;
    }

    /**
     * SHA1加密
     */
    private String SHA1(final String value) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            digest.update(value.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 发送请求
     */
    private JSONObject httpGetForJson(String url) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return JSONObject.parseObject(result);
    }

}