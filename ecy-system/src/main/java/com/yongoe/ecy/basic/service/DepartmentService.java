package com.yongoe.ecy.basic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.basic.entity.Department;

import java.util.List;

/**
 * 部门
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface DepartmentService extends IService<Department> {

    List<Department> getMenuByTree(LambdaQueryWrapper<Department> wrapper);
}
