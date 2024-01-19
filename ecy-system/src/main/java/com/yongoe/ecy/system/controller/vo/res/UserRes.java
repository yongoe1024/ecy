package com.yongoe.ecy.system.controller.vo.res;

import com.yongoe.ecy.system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {

    private Long id;
    /**
     * 部门id
     */
    private Long departmentId;
    /**
     * 职位id
     */
    private Long positionId;
    /**
     * 部门名
     */
    private String departmentName;
    /**
     * 职位名
     */
    private String positionName;
    /**
     * 账号
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 上次登录ip
     */
    private String lastIp;
    /**
     * 上次登录时间
     */
    private LocalDateTime lastTime;
    /**
     * 是否启用
     */
    private Boolean enabled;

    private List<Role> roleList;
}