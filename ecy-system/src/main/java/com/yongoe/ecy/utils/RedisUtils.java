package com.yongoe.ecy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate redisTemplate;


}
