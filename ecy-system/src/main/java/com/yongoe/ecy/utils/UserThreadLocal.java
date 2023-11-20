package com.yongoe.ecy.utils;

import com.yongoe.ecy.system.entity.User;

/**
 * ThreadLocal线程工具
 *
 * @author yongoe
 * @since 2023/1/1
 */
public class UserThreadLocal {

    private UserThreadLocal() {
    }

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user) {
        LOCAL.set(user);
    }

    public static User get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}