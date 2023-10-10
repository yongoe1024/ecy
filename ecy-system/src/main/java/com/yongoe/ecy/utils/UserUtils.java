package com.yongoe.ecy.utils;

import com.yongoe.ecy.config.security.UserThreadLocal;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.User;

/**
 * 用户工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
public class UserUtils {

    public static User getUser() {
        return UserThreadLocal.get();
    }

    public static String getName() {
        return getUser() == null ? "系统" : getUser().getName();
    }

    public static String getUserName() {
        return getUser() == null ? null : getUser().getUsername();
    }

    public static Boolean isAdmin() {
        for (Role role : getUser().getRoleList()) {
            if ("admin".equals(role.getCode())) {
                return true;
            }
        }
        return false;
    }

    public static Long getUserId() {
        return getUser() == null ? null : getUser().getId();
    }

    public static void logout() {
        UserThreadLocal.remove();
    }

}
