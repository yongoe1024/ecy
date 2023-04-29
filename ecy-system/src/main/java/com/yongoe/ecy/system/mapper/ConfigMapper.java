package com.yongoe.ecy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

    Page<Config> getConfigByPage(Page<Config> page, @Param("config") Config config);

    List<Config> getConfig();
}
