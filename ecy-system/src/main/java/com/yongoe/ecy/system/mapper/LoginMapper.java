package com.yongoe.ecy.system.mapper;

import com.yongoe.ecy.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface LoginMapper {

    /**
     * 根据标识符得到 User
     */
    User getUserByIdentifier(String identifier);

    /**
     * 根据凭据得到 User
     */
    User getUserByCredential(String credential);
}
