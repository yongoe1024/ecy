package com.yongoe.ecy.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.system.entity.Log;

/**
 * 系统日志
 *
 * @author yongoe
 * @since 2023/09/19
 */
public interface LogService extends IService<Log> {

    Page<Log> getLogByPage(Page<Log> page, Log log);

}
