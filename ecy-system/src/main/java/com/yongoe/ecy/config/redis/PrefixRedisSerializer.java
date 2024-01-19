package com.yongoe.ecy.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class PrefixRedisSerializer extends StringRedisSerializer {
    @Value("${server.servlet.context-path}")
    private String pathPrefix;

    /**
     * 序列化
     *
     * @param s key
     * @return 结果
     */
    @Override
    public byte[] serialize(String s) {

        if (s == null) {
            return new byte[0];
        }
        String realKey = pathPrefix + ":" + s;
        return super.serialize(realKey);
    }

    /**
     * 反序列化
     *
     * @param bytes 数据
     * @return 结果
     */
    @Override
    public String deserialize(byte[] bytes) {
        String cacheKey = new String(bytes);
        int indexOf = cacheKey.indexOf(pathPrefix + ":");
        if (indexOf == -1) {
            cacheKey = pathPrefix + ":" + cacheKey;
        }
        return cacheKey;
    }
}