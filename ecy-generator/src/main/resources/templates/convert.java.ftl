package com.yongoe.ecy.${packageName}.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.${packageName}.controller.vo.excel.${entity}ExcelVo;
import com.yongoe.ecy.${packageName}.controller.vo.req.${entity}ReqVo;
import com.yongoe.ecy.${packageName}.controller.vo.res.${entity}ResVo;
import com.yongoe.ecy.${packageName}.entity.${entity};
import org.mapstruct.Mapper;

import java.util.List;

/**
 * ${moduleName} convert
 *
 * @author ${author}
 * @since ${createTime}
 */
@Mapper(componentModel = "spring")
public interface ${entity}Convert {
    /**
     * entity转vo
     */
    ${entity}ReqVo entity2Req(${entity} entity);

    ${entity}ResVo entity2Res(${entity} entity);

    ${entity}ExcelVo entity2Excel(${entity} entity);

    /**
     * entity List 转vo
     */
    List<${entity}ReqVo> entity2ReqList(List<${entity}> list);

    List<${entity}ResVo> entity2ResList(List<${entity}> list);

    List<${entity}ExcelVo> entity2ExcelList(List<${entity}> list);

    /**
     * entity Page 转vo
     */
    Page<${entity}ResVo> entity2ResPage(Page<${entity}> page);

    Page<${entity}ExcelVo> entity2ExcelPage(Page<${entity}> page);

    /**
     * req 转 entity
     */
    ${entity}  req2Entity(${entity}ReqVo reqVo);


}