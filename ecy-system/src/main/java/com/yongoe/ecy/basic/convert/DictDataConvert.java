package com.yongoe.ecy.basic.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DictDataReq;
import com.yongoe.ecy.basic.controller.vo.res.DictDataRes;
import com.yongoe.ecy.basic.entity.DictData;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据字典 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface DictDataConvert {
    /**
     * entity to vo
     */
    DictDataReq entity2Req(DictData entity);

    DictDataRes entity2Res(DictData entity);


    /**
     * entity to vo List
     */
    List<DictDataReq> entity2ReqList(List<DictData> list);

    List<DictDataRes> entity2ResList(List<DictData> list);


    /**
     * entity to vo Page
     */
    Page<DictDataRes> entity2ResPage(Page<DictData> page);


    /**
     * vo 转 entity
     */
    DictData req2Entity(DictDataReq reqVo);


}