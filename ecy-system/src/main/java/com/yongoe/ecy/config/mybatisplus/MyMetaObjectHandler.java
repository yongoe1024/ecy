package com.yongoe.ecy.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yongoe.ecy.config.security.UserThreadLocal;
import com.yongoe.ecy.system.entity.User;
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
        User user = UserThreadLocal.get();
        if (user == null) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill(metaObject, "createBy", String.class, "无");
            this.strictInsertFill(metaObject, "updateBy", String.class, "无");
            return;
        }
        String name = user.getName();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, name);
        this.strictInsertFill(metaObject, "updateBy", String.class, name);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = UserThreadLocal.get();
        if (user == null) {
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill(metaObject, "updateBy", String.class, "无");
            return;
        }
        String name = user.getName();
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateBy", String.class, name);
    }
}

