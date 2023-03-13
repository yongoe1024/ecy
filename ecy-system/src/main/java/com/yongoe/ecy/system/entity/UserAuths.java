package com.yongoe.ecy.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户-权限
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@TableName("sys_user_auths")
@Schema(name = "用户-权限")
public class UserAuths {
    @TableId(value="id",type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String identityType;

    private String identifier;

    private String credential;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    public UserAuths() {
    }

    public UserAuths(Long userId, String identityType, String identifier, String credential) {
        this.userId = userId;
        this.identityType = identityType;
        this.identifier = identifier;
        this.credential = credential;
    }
}