package com.yongoe.ecy.basic.controller.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "部门Req")
public class DepartmentReq {

    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 是否启用
     */
    private Boolean enabled;

}