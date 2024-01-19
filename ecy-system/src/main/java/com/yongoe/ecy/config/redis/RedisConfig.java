package com.yongoe.ecy.config.redis;

import jakarta.annotation.Resource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Configuration
@EnableCaching
public class RedisConfig {
    @Resource
    private PrefixRedisSerializer prefixRedisSerializer;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // string类型 key序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // String类型，value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hash类型，key序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash类型，value序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        //统一前缀
        redisTemplate.setKeySerializer(prefixRedisSerializer);
        return redisTemplate;
    }

}
