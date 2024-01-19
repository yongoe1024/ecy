package com.yongoe.ecy.basic.controller.vo.req;

import com.yongoe.ecy.utils.BaseEntity;
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
public class PositionReq extends BaseEntity {

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