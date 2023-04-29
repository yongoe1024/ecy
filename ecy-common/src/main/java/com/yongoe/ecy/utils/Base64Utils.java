package com.yongoe.ecy.utils;

import cn.hutool.core.codec.Base64;

import java.nio.charset.StandardCharsets;

/**
 * base64工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
public class Base64Utils {

    public static String getBase64Encode(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        byte[] bt = str.getBytes(StandardCharsets.UTF_8);
        str = String.valueOf(Base64.encode(bt));
        return str;
    }

    public static String getBase64Decode(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        char[] ch = str.toCharArray();
        byte[] bt = Base64.decode(String.valueOf(ch));
        str = new String(bt, StandardCharsets.UTF_8);
        return str;
    }
}
