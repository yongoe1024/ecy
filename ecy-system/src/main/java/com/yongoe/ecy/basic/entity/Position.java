package com.yongoe.ecy.basic.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 职位
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@TableName("basic_position")
@Schema(name = "职位")
public class Position {
    @TableId(value="id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Boolean enabled;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}