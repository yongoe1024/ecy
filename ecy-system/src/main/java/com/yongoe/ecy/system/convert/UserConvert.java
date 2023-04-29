package com.yongoe.ecy.system.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.req.UserReqVo;
import com.yongoe.ecy.system.controller.vo.res.UserInfoResVo;
import com.yongoe.ecy.system.controller.vo.res.UserResVo;
import com.yongoe.ecy.system.entity.User;
import org.mapstruct.Mapper;

/**
 * 用户 convert
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    Page<UserResVo> entity2ResPage(Page<User> page);

    User req2Entity(UserReqVo reqVo);

    UserInfoResVo entity2UserInfo(User entity);


}