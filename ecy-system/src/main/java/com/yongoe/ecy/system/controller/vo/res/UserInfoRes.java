package com.yongoe.ecy.system.controller.vo.res;

import com.yongoe.ecy.system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户个人信息
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRes {

    private Long id;
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

    private List<Role> roleList;
}
