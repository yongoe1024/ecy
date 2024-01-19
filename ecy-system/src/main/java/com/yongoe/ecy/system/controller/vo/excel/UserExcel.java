package com.yongoe.ecy.system.controller.vo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserExcel
 *
 * @author yongoe
 * @since 2023/12/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExcel {

    @Excel(name = "部门", width = 20)
    private String departmentName;

    @Excel(name = "职位", width = 20)
    private String positionName;

    @Excel(name = "账号", width = 20)
    private String username;

    @Excel(name = "密码", width = 20)
    private String password;

    @Excel(name = "姓名", width = 20)
    private String name;

    @Excel(name = "邮箱", width = 20)
    private String email;

    @Excel(name = "联系电话", width = 20)
    private String phone;

    @Excel(name = "角色", width = 20)
    private String roleName;

}
