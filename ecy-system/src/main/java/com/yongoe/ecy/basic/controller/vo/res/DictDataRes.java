package com.yongoe.ecy.basic.controller.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据字典-数据
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictDataRes {

    private Long id;
    /**
     * 字典id
     */
    private Long dictId;
    /**
     * 字典键
     */
    private String dictKey;
    /**
     * 字典值
     */
    private String dictValue;
    /**
     * 颜色
     */
    private String color;
    /**
     * 字典顺序
     */
    private Integer sort;

}