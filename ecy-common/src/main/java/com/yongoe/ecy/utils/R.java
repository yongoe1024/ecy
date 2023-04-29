package com.yongoe.ecy.utils;

import lombok.Data;

/**
 * 统一返回类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
public class R {

    int code;
    String message;
    Object data;

    public static R success() {
        R r = new R();
        r.code = 200;
        return r;
    }

    public static R success(String message) {
        R r = new R();
        r.code = 200;
        r.message = message;
        return r;
    }

    public static R error() {
        R r = new R();
        r.code = 500;
        r.message = "请求失败";
        return r;
    }

    public static R error(String message) {
        R r = new R();
        r.code = 500;
        r.message = message;
        return r;
    }

    public static R error(int code, String message) {
        R r = new R();
        r.code = code;
        r.message = message;
        return r;
    }

    public R put(Object data) {
        this.data = data;
        return this;
    }

}
