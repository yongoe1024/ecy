package com.yongoe.ecy.utils;

import com.yongoe.ecy.config.security.UserThreadLocal;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.User;

import java.util.ArrayList;
import java.util.List;

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

    public static Boolean isRole(String code) {
        List<Role> roles = getUser() == null ? new ArrayList<>() : getUser().getRoleList();
        for (Role role : roles) {
            if ("code".equals(role.getCode())) {
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
