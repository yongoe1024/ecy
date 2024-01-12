package com.yongoe.ecy.system.controller.vo.req;

import com.yongoe.ecy.utils.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "角色Req")
public class RoleReq extends BaseEntity {

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 代码
     */
    private String code;
}