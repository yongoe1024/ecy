package com.yongoe.ecy.basic.controller.vo.res;

import com.yongoe.ecy.basic.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 部门
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRes {

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


    private List<Department> children;
}