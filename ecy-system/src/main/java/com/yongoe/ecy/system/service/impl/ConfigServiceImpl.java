package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.system.entity.Config;
import com.yongoe.ecy.system.mapper.ConfigMapper;
import com.yongoe.ecy.system.service.ConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public Page<Config> getConfigByPage(Page<Config> page, Config config) {
        return baseMapper.getConfigByPage(page, config);
    }

    @Override
    public List<Config> getConfig() {
        return baseMapper.getConfig();
    }

}
