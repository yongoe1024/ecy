package com.yongoe.ecy.utils;

import java.lang.reflect.Field;

/**
 * Object对象工具类
 */
public class ObjectUitls {

    /**
     * 判断对象中属性是否全为空
     */
    public static boolean isAllFieldNull(Object obj) {
        // 得到类对象
        Class<?> stuCla = obj.getClass();
        //得到属性集合
        Field[] fs = stuCla.getDeclaredFields();
        boolean flag = true;
        //遍历属性
        for (Field f : fs) {
            // 设置属性是可以访问的(私有的也可以)
            f.setAccessible(true);
            // 得到此属性的值
            Object val = null;
            try {
                val = f.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            //只要有1个属性不为空,那么就不是所有的属性值都为空
            if (val != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }

}