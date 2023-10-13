package com.yongoe.ecy.basic.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.PositionReqVo;
import com.yongoe.ecy.basic.controller.vo.res.PositionResVo;
import com.yongoe.ecy.basic.entity.Position;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 职位 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface PositionConvert {
    /**
     * entity to vo
     */
    PositionReqVo entity2Req(Position entity);

    PositionResVo entity2Res(Position entity);


    /**
     * entity to vo List
     */
    List<PositionReqVo> entity2ReqList(List<Position> list);

    List<PositionResVo> entity2ResList(List<Position> list);


    /**
     * entity to vo Page
     */
    Page<PositionResVo> entity2ResPage(Page<Position> page);


   /**
     * vo 转 entity
     */
    Position req2Entity(PositionReqVo reqVo);


}