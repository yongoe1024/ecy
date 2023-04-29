package com.yongoe.ecy.utils;

import com.yongoe.ecy.config.security.UserThreadLocal;
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

    public static Long getUserId() {
        return UserThreadLocal.get().getId();
    }

    public static void logout() {
        UserThreadLocal.remove();
    }

}
