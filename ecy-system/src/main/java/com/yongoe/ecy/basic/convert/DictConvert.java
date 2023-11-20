package com.yongoe.ecy.basic.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DictReq;
import com.yongoe.ecy.basic.controller.vo.res.DictRes;
import com.yongoe.ecy.basic.entity.Dict;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据字典 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface DictConvert {
    /**
     * entity to vo
     */
    DictReq entity2Req(Dict entity);

    DictRes entity2Res(Dict entity);


    /**
     * entity to vo List
     */
    List<DictReq> entity2ReqList(List<Dict> list);

    List<DictRes> entity2ResList(List<Dict> list);


    /**
     * entity to vo Page
     */
    Page<DictRes> entity2ResPage(Page<Dict> page);


    /**
     * vo 转 entity
     */
    Dict req2Entity(DictReq reqVo);


}