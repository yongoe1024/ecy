package com.yongoe.ecy.basic.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DictReqVo;
import com.yongoe.ecy.basic.controller.vo.res.DictResVo;
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
    DictReqVo entity2Req(Dict entity);

    DictResVo entity2Res(Dict entity);


    /**
     * entity to vo List
     */
    List<DictReqVo> entity2ReqList(List<Dict> list);

    List<DictResVo> entity2ResList(List<Dict> list);


    /**
     * entity to vo Page
     */
    Page<DictResVo> entity2ResPage(Page<Dict> page);


   /**
     * vo 转 entity
     */
    Dict req2Entity(DictReqVo reqVo);


}