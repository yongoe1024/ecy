package com.yongoe.ecy.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.basic.entity.Department;
import com.yongoe.ecy.basic.mapper.DepartmentMapper;
import com.yongoe.ecy.basic.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    public List<Department> getMenuByTree(LambdaQueryWrapper<Department> wrapper) {
        List<Department> departments = baseMapper.selectList(wrapper);
        return tree(0L, departments);
    }

    private List<Department> tree(Long rootId, List<Department> all) {
        return all.stream()
                .filter(entity -> entity.getParentId().longValue() == rootId.longValue())
                .peek(entity -> {
                    List<Department> ch = tree(entity.getId(), all);
                    entity.setChildren(ch);
                })
                .collect(Collectors.toList());
    }
}
