package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.system.entity.Log;
import com.yongoe.ecy.system.mapper.LogMapper;
import com.yongoe.ecy.system.service.LogService;
import org.springframework.stereotype.Service;

/**
 * 系统日志
 *
 * @author yongoe
 * @since 2023/09/19
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public Page<Log> getLogByPage(Page<Log> page, Log log) {
        return baseMapper.getLogByPage(page, log);
    }

}
