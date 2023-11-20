package com.yongoe.ecy.${packageName}.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.${packageName}.controller.vo.excel.${entity}Excel;
import com.yongoe.ecy.${packageName}.controller.vo.req.${entity}Req;
import com.yongoe.ecy.${packageName}.controller.vo.res.${entity}Res;
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
     * entity to vo
     */
    ${entity}Req entity2Req(${entity} entity);

    ${entity}Res entity2Res(${entity} entity);

    ${entity}Excel entity2Excel(${entity} entity);

    /**
     * entity to vo List
     */
    List<${entity}Req> entity2ReqList(List<${entity}> list);

    List<${entity}Res> entity2ResList(List<${entity}> list);

    List<${entity}Excel> entity2ExcelList(List<${entity}> list);

    /**
     * entity to vo Page
     */
    Page<${entity}Res> entity2ResPage(Page<${entity}> page);

    /**
     * vo to entity
     */
    ${entity} req2Entity(${entity}Req req);

    ${entity} excel2Entity(${entity}Excel excel);

    /**
     * vo to entity List
     */
    List<${entity}> excel2EntityList(List<${entity}Excel> list);


}