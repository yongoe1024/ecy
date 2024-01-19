package com.yongoe.ecy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.basic.entity.Department;
import com.yongoe.ecy.basic.entity.Position;
import com.yongoe.ecy.basic.service.DepartmentService;
import com.yongoe.ecy.basic.service.PositionService;
import com.yongoe.ecy.system.controller.vo.excel.UserExcel;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.entity.UserAuths;
import com.yongoe.ecy.system.entity.UserRole;
import com.yongoe.ecy.system.mapper.UserAuthsMapper;
import com.yongoe.ecy.system.mapper.UserMapper;
import com.yongoe.ecy.system.mapper.UserRoleMapper;
import com.yongoe.ecy.system.service.RoleService;
import com.yongoe.ecy.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserAuthsMapper userAuthsMapper;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private PositionService positionService;
    @Resource
    private RoleService roleService;

    @Override
    public Page<User> getUserByPage(Page<User> page, User user) {
        return baseMapper.getUserByPage(page, user);
    }

    @Override
    public User getUserById(Long id) {
        return baseMapper.getUserById(id);
    }

    @Override
    public void updateUserRole(Long userId, List<Long> roleIds) {
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        if (!CollectionUtils.isEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                userRoleMapper.insert(new UserRole(userId, roleId));
            }
        }
    }

    @Override
    public void upload(List<UserExcel> excelList) {
        for (UserExcel excel : excelList) {
            User user = new User();
            //查询部门
            Department department = departmentService.getOne(new LambdaUpdateWrapper<Department>().eq(Department::getName, excel.getDepartmentName()));
            Position position = positionService.getOne(new LambdaUpdateWrapper<Position>().eq(Position::getName, excel.getPositionName()));
            Role role = roleService.getOne(new LambdaUpdateWrapper<Role>().eq(Role::getName, excel.getRoleName()));
            if (department != null)
                user.setDepartmentId(department.getId());
            if (position != null)
                user.setPositionId(position.getId());
            user.setUsername(excel.getUsername());
            user.setPassword(excel.getPassword());
            user.setName(excel.getName());
            user.setAvatar("/");
            user.setPhone(excel.getPhone());
            user.setEmail(excel.getEmail());
            user.setEnabled(true);
            this.save(user);
            if (role != null) {
                List<Long> list = List.of(role.getId());
                this.updateUserRole(user.getId(), list);
            }
        }
    }

    @Override
    public void deleteUser(Long id) {
        userAuthsMapper.delete(new LambdaQueryWrapper<UserAuths>().eq(UserAuths::getUserId, id));
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
        this.removeById(id);
    }

}
