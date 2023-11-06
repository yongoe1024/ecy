package com.yongoe.ecy.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yongoe.ecy.system.entity.Config;
import com.yongoe.ecy.system.service.ConfigService;
import jakarta.annotation.Resource;
import me.zhyd.oauth.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取系统配置的工具
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class SysConfigUtils {
    @Resource
    private ConfigService configService;

    private Map<String, String> configMap = new HashMap<>();

    /**
     * 获取配置
     */
    public String get(String key) {
        String value = configMap.get(key);
        if (StringUtils.isEmpty(value)) {
            init();
            value = configMap.get(key);
            if (StringUtils.isEmpty(value)) {
                throw new RuntimeException("数据库没有配置 => " + key);
            }
        }
        return value;
    }

    public void set(String key, String value) {
        Config config_key = configService.getOne(new QueryWrapper<Config>().eq("config_key", key));
        config_key.setConfigValue(value);
        configService.updateById(config_key);
        init();
    }

    /**
     * 初始化，刷新数据库信息
     */
    public void init() {
        Map<String, String> map = new HashMap<>();
        for (Config config : configService.getConfig()) {
            String configKey = config.getConfigKey();
            String configValue = config.getConfigValue();
            map.put(configKey, configValue);
        }
        this.configMap = map;
    }

}
