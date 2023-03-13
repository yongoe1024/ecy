package com.yongoe.ecy.basic.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据字典
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@TableName("basic_dict")
@Schema(name = "数据字典")
public class Dict {
    @TableId(value="id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer type;

    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}