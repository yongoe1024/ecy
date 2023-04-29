package com.yongoe.ecy.system.controller.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Schema(name = "用户ReqVo")
public class UserReqVo {

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
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
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
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 修改角色所用
     */
    private List<Long> roleIds;
}