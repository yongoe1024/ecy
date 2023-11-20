package com.yongoe.ecy.config.aop;

import com.yongoe.ecy.system.service.LogService;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志切面
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
@Aspect
public class Logger {
    @Resource
    private LogService logService;

    /**
     * 在  包下的类 的 方法，都能注入代码
     * before -> afterReturning -> after
     */
    @Pointcut("execution(* com.yongoe.ecy..controller.*.*(..))")
    public void pt() {
    }

    @Pointcut("pt()")
    public void all() {
    }

    /**
     * 执行之前 会先执行下面方法
     */
    @Before("all()")
    public void beforePrint() {
    }

    /**
     * ，执行之后（不论是否抛异常） 会执行下面方法
     */
    @After("all()")
    public void afterPrint() {
    }


    /**
     * 执行之后（没抛异常） 会执行下面方法
     */
    @AfterReturning("all()")
    public void afterReturningPrint(JoinPoint joinPoint) {
//        String classname = joinPoint.getTarget().getClass().getName();
//        String methodname = joinPoint.getSignature().getName();
//        String s = Arrays.toString(joinPoint.getArgs());
//        logService.saveLog("操作", "执行了 " + classname + " 的 " + methodname + " 方法",
//                "参数：" + s + "\n");
    }

    /**
     * 执行之后（抛异常了） 会执行下面方法
     */
    @AfterThrowing(value = "all()", throwing = "throwable")
    public void afterThrowingPrint(JoinPoint joinPoint, Throwable throwable) {
        String classname = joinPoint.getTarget().getClass().getName();
        String methodname = joinPoint.getSignature().getName();
        String s = Arrays.toString(joinPoint.getArgs());
        logService.saveLog("异常", "在 " + classname + " 的 " + methodname + " 方法中发生了异常",
                "参数：" + s + "\n" + throwable);
    }

}
