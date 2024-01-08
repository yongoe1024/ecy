package com.yongoe.ecy.basic.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 数据字典-数据
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("basic_dict_data")
@Schema(name = "数据字典-数据")
public class DictData {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long dictId;

    private String dictKey;

    private String color;

    private String dictValue;

    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}