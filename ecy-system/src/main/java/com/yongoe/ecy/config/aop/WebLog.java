package com.yongoe.ecy.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebLog {

    /**
     * 忽略出入参数
     */
    boolean ignore() default true;

    /**
     * 描述，默认用 （系统日志-删除数据）
     * " @Tag(name = "系统日志") @Operation(summary = "删除数据") "
     */
    String description() default "";

}