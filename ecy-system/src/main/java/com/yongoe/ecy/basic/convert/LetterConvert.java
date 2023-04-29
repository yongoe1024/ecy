package com.yongoe.ecy.basic.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.LetterReqVo;
import com.yongoe.ecy.basic.controller.vo.res.LetterResVo;
import com.yongoe.ecy.basic.entity.Letter;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 信件 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface LetterConvert {
    /**
     * entity转vo
     */
    LetterReqVo entity2Req(Letter entity);

    LetterResVo entity2Res(Letter entity);

    /**
     * entity List 转vo
     */
    List<LetterReqVo> entity2ReqList(List<Letter> list);

    List<LetterResVo> entity2ResList(List<Letter> list);


    /**
     * entity Page 转vo
     */
    Page<LetterResVo> entity2ResPage(Page<Letter> page);


    /**
     * req 转 entity
     */
    Letter req2Entity(LetterReqVo reqVo);


}