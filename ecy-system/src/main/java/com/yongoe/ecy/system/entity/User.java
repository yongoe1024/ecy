package com.yongoe.ecy.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long departmentId;

    private Long positionId;

    @TableField(exist = false)
    private String departmentName;

    @TableField(exist = false)
    private String positionName;

    private String username;

    private String password;

    private String name;

    private String avatar;

    private String email;

    private String phone;

    private String remark;

    private String lastIp;

    private LocalDateTime lastTime;

    private Boolean enabled;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(exist = false)
    private List<Role> roleList;

}