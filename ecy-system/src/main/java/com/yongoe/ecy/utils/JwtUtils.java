package com.yongoe.ecy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
public class JwtUtils {
    public static final String requestHeader = "Authorization";  // JWT存储的请求头
    public static final String tokenHead = "Bearer";           // JWT 负载中拿到开头

    private static final String secret = "ecy-secret";          // JWT 加解密使用的密钥;
    private static final Long expiration = 604800L;             //  JWT的超期限时间（60*60*24）1Day

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 根据用户信息生成token
     */
    public static String createToken(String key) {
        Map<String, Object> claim = new HashMap<>();
        claim.put(CLAIM_KEY_USERNAME, key);
        claim.put(CLAIM_KEY_CREATED, new Date());
        return tokenHead + generateToken(claim);
    }


    /**
     * 从token获取用户名,token错误则为null
     */
    public static String getKeyFromToken(String token) {
        String key = null;
        Claims claims = getClaimsFormToken(token);
        if (claims != null) {
            key = claims.getSubject();
        }
        return key;

    }

    /**
     * 从token获取荷载 过期抛异常
     */
    private static Claims getClaimsFormToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据荷载生成 token
     */
    private static String generateToken(Map<String, Object> claim) {
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 生成token失效时间
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

//          没啥用
//    /**
//     * 验证token是否有效，是否过期，数据库是否存在
//     */
//    public boolean validateToken(User user, String token) {
//        String userkey = user.getUserkey();
//        String key = getUserNameFromToken(token);
//        return userkey.equals(key) && !isTokenExpired(token);
//    }
//    /**
//     * 判断token是否失效
//     */
//    private boolean isTokenExpired(String token) {
//        Claims claims = getClaimsFormToken(token);
//        Date expireDate = claims.getExpiration();
//        return expireDate.before(new Date());
//    }
}
