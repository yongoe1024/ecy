package com.yongoe.ecy.system.controller.vo.req;

import com.yongoe.ecy.utils.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志
 *
 * @author yongoe
 * @since 2023/09/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "系统日志Req")
public class LogReq extends BaseEntity {

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

}