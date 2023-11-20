package com.yongoe.ecy.system.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.req.ConfigReq;
import com.yongoe.ecy.system.controller.vo.res.ConfigRes;
import com.yongoe.ecy.system.entity.Config;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 系统配置 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface ConfigConvert {
    /**
     * entity to vo
     */
    ConfigReq entity2Req(Config entity);

    ConfigRes entity2Res(Config entity);


    /**
     * entity to vo List
     */
    List<ConfigReq> entity2ReqList(List<Config> list);

    List<ConfigRes> entity2ResList(List<Config> list);


    /**
     * entity to vo Page
     */
    Page<ConfigRes> entity2ResPage(Page<Config> page);


    /**
     * vo 转 entity
     */
    Config req2Entity(ConfigReq reqVo);


}