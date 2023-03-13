package com.yongoe.ecy.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@TableName("sys_role")
@Schema(name = "角色")
public class Role {
    @TableId(value="id",type = IdType.AUTO)
    private Long id;

    private String name;

    private String nameZh;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(exist = false)
    private List<Long> menuIds;

}