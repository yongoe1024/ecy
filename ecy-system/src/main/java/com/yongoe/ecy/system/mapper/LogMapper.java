package com.yongoe.ecy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统日志
 *
 * @author yongoe
 * @since 2023/09/19
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

    Page<Log> getLogByPage(Page<Log> page, @Param("log") Log log);

}
