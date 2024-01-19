package com.yongoe.ecy.basic.controller.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 职位
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionRes {

    private Long id;
    /**
     * 职位
     */
    private String name;
    /**
     * 是否启用
     */
    private Boolean enabled;

}