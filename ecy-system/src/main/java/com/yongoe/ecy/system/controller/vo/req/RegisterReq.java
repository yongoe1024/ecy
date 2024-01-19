package com.yongoe.ecy.system.controller.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册信息
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReq {

    String username;

    String password;

    String name;

    String email;

    String phone;


}
