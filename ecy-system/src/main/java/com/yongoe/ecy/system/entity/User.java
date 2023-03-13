package com.yongoe.ecy.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@TableName("sys_user")
@Schema(name = "用户")
public class User {
    @TableId(value="id",type = IdType.AUTO)
    private Long id;

    private String name;

    private String avatar;

    private String email;

    private String phone;

    private String remark;

    private LocalDateTime lastTime;

    private Boolean enabled;

    @TableField(exist = false)
    private List<Role> roleList;

    @TableField(exist = false)
    private List<Long> roleIds;

    @TableField(exist = false)
    private String identityType;

    @TableField(exist = false)
    private String identifier;

    @TableField(exist = false)
    private String credential;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;


}