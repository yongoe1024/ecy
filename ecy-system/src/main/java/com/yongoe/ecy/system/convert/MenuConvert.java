package com.yongoe.ecy.system.convert;

import com.yongoe.ecy.system.controller.vo.req.MenuReq;
import com.yongoe.ecy.system.controller.vo.res.MenuRes;
import com.yongoe.ecy.system.entity.Menu;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 菜单 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface MenuConvert {

    Menu req2Entity(MenuReq reqVo);


    List<MenuRes> entity2Res(List<Menu> entity);
}