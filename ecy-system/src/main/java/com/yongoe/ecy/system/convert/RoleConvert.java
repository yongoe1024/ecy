package com.yongoe.ecy.system.convert;

import com.yongoe.ecy.system.controller.vo.req.RoleReqVo;
import com.yongoe.ecy.system.controller.vo.res.RoleResVo;
import com.yongoe.ecy.system.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 角色 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface RoleConvert {

    Role req2Entity(RoleReqVo reqVo);


    List<RoleResVo> entity2Res(List<Role> list);
}