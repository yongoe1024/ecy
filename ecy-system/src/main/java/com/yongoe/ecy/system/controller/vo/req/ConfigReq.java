package com.yongoe.ecy.system.controller.vo.req;

import com.yongoe.ecy.utils.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "系统配置Req")
public class ConfigReq extends BaseEntity {

    private Long id;
    /**
     * 键
     */
    private String configKey;
    /**
     * 值
     */
    private String configValue;
    /**
     * 备注
     */
    private String remark;
}