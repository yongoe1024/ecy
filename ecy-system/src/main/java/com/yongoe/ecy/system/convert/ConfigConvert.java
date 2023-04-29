package com.yongoe.ecy.system.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.req.ConfigReqVo;
import com.yongoe.ecy.system.controller.vo.res.ConfigResVo;
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
     * entity转vo
     */
    ConfigReqVo entity2Req(Config entity);

    ConfigResVo entity2Res(Config entity);


    /**
     * entity List 转vo
     */
    List<ConfigReqVo> entity2ReqList(List<Config> list);

    List<ConfigResVo> entity2ResList(List<Config> list);


    /**
     * entity Page 转vo
     */
    Page<ConfigResVo> entity2ResPage(Page<Config> page);


    /**
     * req 转 entity
     */
    Config req2Entity(ConfigReqVo reqVo);


}