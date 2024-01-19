package com.yongoe.ecy.system.controller.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统日志
 *
 * @author yongoe
 * @since 2023/09/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRes {

    /**
     *
     */
    private Long id;
    /**
     * 用户
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 标题
     */
    private String title;
    /**
     * 详情
     */
    private String details;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改人
     */
    private String updateBy;
}