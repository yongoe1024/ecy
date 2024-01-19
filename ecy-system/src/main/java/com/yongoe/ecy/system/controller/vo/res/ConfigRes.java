package com.yongoe.ecy.system.controller.vo.res;

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
public class ConfigRes {

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