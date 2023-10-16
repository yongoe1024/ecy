package com.yongoe.ecy.system.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.req.LogReqVo;
import com.yongoe.ecy.system.controller.vo.res.LogResVo;
import com.yongoe.ecy.system.entity.Log;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 系统日志 convert
 *
 * @author yongoe
 * @since 2023/09/19
 */
@Mapper(componentModel = "spring")
public interface LogConvert {
    /**
     * entity to vo
     */
    LogReqVo entity2Req(Log entity);

    LogResVo entity2Res(Log entity);

    /**
     * entity to vo List
     */
    List<LogReqVo> entity2ReqList(List<Log> list);

    List<LogResVo> entity2ResList(List<Log> list);

    /**
     * entity to vo Page
     */
    Page<LogResVo> entity2ResPage(Page<Log> page);

    /**
     * vo 转 entity
     */
    Log req2Entity(LogReqVo reqVo);


}