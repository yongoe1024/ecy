package com.yongoe.ecy.config.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yongoe.ecy.system.entity.Log;
import com.yongoe.ecy.system.service.LogService;
import com.yongoe.ecy.utils.IPUtils;
import com.yongoe.ecy.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;

/**
 * 日志切面
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
@Aspect
@Order(0)
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

    /**
     * 执行之前 会先执行下面方法
     */
    @Before("pt()")
    public void beforePrint() {
    }

    /**
     * ，执行之后（不论是否抛异常） 会执行下面方法
     */
    @After("pt()")
    public void afterPrint() {
    }


    /**
     * 执行之后（没抛异常） 会执行下面方法
     */
    @AfterReturning("pt()")
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
    @AfterThrowing(value = "pt()", throwing = "throwable")
    public void afterThrowingPrint(JoinPoint joinPoint, Throwable throwable) {
        String classname = joinPoint.getTarget().getClass().getName();
        String methodname = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        this.saveThrowable("在 " + classname + " 的 " + methodname + " 方法中发生了异常",
                "参数：" + args + "\n" + throwable);
    }


    /**
     * 注解Log切面
     */
    @Pointcut("@annotation(com.yongoe.ecy.config.aop.WebLog)")
    public void doLogPointCut() {
    }

    @Around("doLogPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long t1 = System.currentTimeMillis();
        Object result = point.proceed();//调用本类内部切入点对应其它通知或其它切面或目标方法。
        long t2 = System.currentTimeMillis();
        saveLog(point, (t2 - t1), result);
        return result;
    }


    private void saveLog(ProceedingJoinPoint point, long time, Object result) {
        Tag tag = point.getTarget().getClass().getAnnotation(Tag.class);
        String tagName = tag.name();
        String classname = point.getTarget().getClass().getName();
        String method = point.getSignature().getName();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Operation operation = methodSignature.getMethod().getAnnotation(Operation.class);
        String summary = operation.summary();
        WebLog annotation = methodSignature.getMethod().getAnnotation(WebLog.class);
        boolean ignore = annotation.ignore();
        String description = annotation.description();

        Log entity = new Log();
        entity.setName(this.getName());
        entity.setType("操作");
        if (!StringUtils.isEmpty(summary)) {
            entity.setTitle(StringUtils.isEmpty(tagName) ? "" : tagName + " - " + summary);
        } else if (!StringUtils.isEmpty(description)) {
            entity.setTitle(description);
        } else {
            entity.setTitle(classname + "-" + method);
        }
        JSONObject jsonObject = new JSONObject();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        jsonObject.put("耗时", time + " ms");
        jsonObject.put("IP", IPUtils.getIp(request));
        jsonObject.put("类名", classname);
        jsonObject.put("方法名", method);
        if (!ignore) {
            jsonObject.put("入参", Arrays.toString(point.getArgs()));
            jsonObject.put("出参", result);
        }
        entity.setDetails(JSONObject.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
        logService.save(entity);
    }

    public void saveThrowable(String title, String details) {
        try {
            Log log = new Log();
            log.setTitle(title);
            log.setDetails(details);
            log.setName(this.getName());
            log.setType("异常");
            logService.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getName() {
        if (UserUtils.getUser() != null)
            return UserUtils.getName();
        else {
            return "系统";
        }
    }

}
