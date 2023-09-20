package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.system.entity.Log;
import com.yongoe.ecy.system.mapper.LogMapper;
import com.yongoe.ecy.system.service.LogService;
import com.yongoe.ecy.utils.UserUtils;
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
    public Page<Log> getLogByPage(Page<Log> page) {
        return baseMapper.getLogByPage(page);
    }

    @Override
    public void saveLog(String type,String title, String details) {
       try {
           Log log = new Log();
           if (title.length() >= 100)
               log.setTitle(title.substring(0, 99));
           else
               log.setTitle(title);
           if (details.length() >= 2000)
               log.setDetails(details.substring(0, 1999));
           else
               log.setDetails(details);
           String name = UserUtils.getName();
           log.setName(name);
           log.setType(type);
           baseMapper.insert(log);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

}
