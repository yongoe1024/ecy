package com.yongoe.ecy.basic.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DepartmentReqVo;
import com.yongoe.ecy.basic.controller.vo.res.DepartmentResVo;
import com.yongoe.ecy.basic.entity.Department;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 部门 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface DepartmentConvert {
    /**
     * entity转vo
     */
    DepartmentReqVo entity2Req(Department entity);

    DepartmentResVo entity2Res(Department entity);


    /**
     * entity List 转vo
     */
    List<DepartmentReqVo> entity2ReqList(List<Department> list);

    List<DepartmentResVo> entity2ResList(List<Department> list);


    /**
     * entity Page 转vo
     */
    Page<DepartmentResVo> entity2ResPage(Page<Department> page);


    /**
     * req 转 entity
     */
    Department req2Entity(DepartmentReqVo reqVo);


}