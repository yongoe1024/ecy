package com.yongoe.ecy.basic.controller.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "数据字典-数据ReqVo")
public class DictDataReqVo {

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
     * 字典顺序
     */
    private Integer sort;

}