package com.yongoe.ecy.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.utils.UserUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus插入时间字段
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        User user = UserUtils.getUser();
        if (user == null) {
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("createBy", "系统", metaObject);
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updateBy", "系统", metaObject);
            return;
        }
        String name = user.getName();
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("createBy", name, metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", name, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = UserUtils.getUser();
        if (user == null) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updateBy", "系统", metaObject);
            return;
        }
        String name = user.getName();
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", name, metaObject);
    }
}

