package com.yongoe.ecy.utils;

import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.User;

import java.util.List;
import java.util.Objects;

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

    public static Long getUserId() {
        User user = getUser();
        Objects.requireNonNull(user, "用户未登录");
        return getUser().getId();
    }

    public static String getName() {
        User user = getUser();
        Objects.requireNonNull(user, "用户未登录");
        return getUser().getName();
    }

    public static String getUserName() {
        User user = getUser();
        Objects.requireNonNull(user, "用户未登录");
        return getUser().getUsername();
    }

    public static Boolean isRole(String code) {
        User user = getUser();
        Objects.requireNonNull(user, "用户未登录");
        List<Role> roles = getUser().getRoleList();
        for (Role role : roles) {
            if (code.equals(role.getCode())) {
                return true;
            }
        }
        return false;
    }


    public static void logout() {
        UserThreadLocal.remove();
    }

}
