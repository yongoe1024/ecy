package com.yongoe.ecy.basic.controller.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据字典
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "数据字典Req")
public class DictReq {

    private Long id;
    /**
     * 字典名称
     */
    private String name;
    /**
     * 字典类型(select,radio)
     */
    private String type;
    /**
     * 备注
     */
    private String remark;

}