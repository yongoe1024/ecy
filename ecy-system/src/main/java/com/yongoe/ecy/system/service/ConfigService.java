package com.yongoe.ecy.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.system.entity.Config;

import java.util.List;

/**
 * 系统配置
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface ConfigService extends IService<Config> {

    Page<Config> getConfigByPage(Page<Config> page, Config config);

    List<Config> getConfig();

}
