package com.yongoe.ecy.basic.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.DepartmentReq;
import com.yongoe.ecy.basic.controller.vo.res.DepartmentRes;
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
     * entity to vo
     */
    DepartmentReq entity2Req(Department entity);

    DepartmentRes entity2Res(Department entity);


    /**
     * entity to vo List
     */
    List<DepartmentReq> entity2ReqList(List<Department> list);

    List<DepartmentRes> entity2ResList(List<Department> list);


    /**
     * entity to vo Page
     */
    Page<DepartmentRes> entity2ResPage(Page<Department> page);


    /**
     * vo 转 entity
     */
    Department req2Entity(DepartmentReq reqVo);


}