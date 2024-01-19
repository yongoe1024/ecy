CREATE DATABASE IF NOT EXISTS `ecy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `ecy`;


DROP TABLE IF EXISTS `basic_department`;

CREATE TABLE `basic_department`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint                                                       NOT NULL COMMENT '父id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门名称',
    `leader`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '负责人',
    `phone`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系电话',
    `enabled`     tinyint(1)                                                   NOT NULL COMMENT '是否启用',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `basic_department`(`id`, `parent_id`, `name`, `leader`, `phone`, `enabled`, `create_time`, `update_time`,
                               `create_by`, `update_by`)
values (1, 0, '董事会', 'yongoe', '10086', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (2, 1, '销售部门', 'yongoe', '10086', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (3, 1, '研发部门', 'yongoe', '10086', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (4, 1, '行政部门', 'yongoe', '10086', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe');


DROP TABLE IF EXISTS `basic_dict`;

CREATE TABLE `basic_dict`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典名称',
    `type`        varchar(50) COLLATE utf8mb4_unicode_ci                       NULL COMMENT '字典类型',
    `remark`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;



DROP TABLE IF EXISTS `basic_dict_data`;

CREATE TABLE `basic_dict_data`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `dict_id`     bigint                                                       NOT NULL COMMENT '字典id',
    `dict_key`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典键',
    `dict_value`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典值',
    `color`       varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '颜色',
    `sort`        int                                                          NOT NULL COMMENT '字典顺序',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `basic_position`;

CREATE TABLE `basic_position`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '职位',
    `enabled`     tinyint(1)                                                   NOT NULL COMMENT '是否启用',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


insert into `basic_position`(`id`, `name`, `enabled`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, '首席执行官(CEO)', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (2, '首席财务官(CFO)', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (3, '首席运营官(COO)', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (4, '首席技术官(CTO)', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe');


DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config`
(
    `id`           bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `config_key`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '键',
    `config_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci    NOT NULL COMMENT '值',
    `remark`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `create_time`  datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time`  datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`    varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`    varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `key` (`config_key`) COMMENT 'key'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


insert into `sys_config`(`id`, `config_key`, `config_value`, `remark`, `create_time`, `update_time`, `create_by`,
                         `update_by`)
values (1, 'qq-clientId', '无', 'QQ登陆clientId(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (2, 'qq-clientSecret', '无', 'QQ登陆Secret(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (3, 'qq-redirectUri', '无', 'QQ登陆重定向链接(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (4, 'sms-secretId', '无', '腾讯云账号id(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (5, 'sms-secretKey', '无', '腾讯云账号key(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (6, 'sms-sdkAppId', '无', '创建应用的id(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (7, 'sms-signName', '无', '签名内容(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (8, 'sms-templateId', '无', '短信模板id(动态生效)', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (9, 'wxh5-clientId', '无', '微信H5登陆clientId(动态生效)', '2023-11-06 05:33:19', '2023-11-06 05:33:21',
        'yongoe',
        'yongoe'),
       (10, 'wxh5-clientSecret', '无', '微信H5登陆Secret(动态生效)', '2023-11-06 05:33:59', '2023-11-06 05:34:01',
        'yongoe',
        'yongoe'),
       (11, 'wxh5-redirectUri', '无', '微信H5登陆redirectUri(动态生效)', '2023-11-06 05:34:24', '2023-11-06 05:34:26',
        'yongoe',
        'yongoe'),
       (12, 'mail-host', 'smtp.qq.com', '邮箱host(重启生效)', '2023-11-08 13:58:06', '2023-11-08 13:58:06', 'yongoe',
        'yongoe'),
       (13, 'mail-port', '25', '邮箱端口(重启生效)', '2023-11-08 13:58:21', '2023-11-08 13:58:21', 'yongoe', 'yongoe'),
       (14, 'mail-username', '无', '邮箱账号(重启生效)', '2023-11-08 13:58:42', '2023-11-08 13:58:42', 'yongoe',
        'yongoe'),
       (15, 'mail-password', '无', '邮箱密钥(重启生效)', '2023-11-08 13:59:01', '2023-11-08 13:59:01', 'yongoe',
        'yongoe'),
       (16, 'mail-protocol', 'smtp', '邮箱协议(重启生效)', '2023-11-08 13:59:17', '2023-11-08 13:59:32', 'yongoe',
        'yongoe'),
       (17, 'alipay-appId', '无', '支付宝应用ID', '2023-12-26 15:43:01', '2023-12-26 15:43:01', 'yongoe', 'yongoe'),
       (18, 'alipay-privateKey', '无', '支付宝商户私钥，PKCS8格式RSA2私钥', '2023-12-26 15:43:43', '2023-12-26 15:50:49',
        'yongoe', 'yongoe'),
       (19, 'alipay-publicKey', '无', '支付宝公钥', '2023-12-26 15:43:58', '2023-12-26 15:51:12', 'yongoe', 'yongoe'),
       (20, 'alipay-notifyUrl', 'http://localhost:8081/alipay/success', '服务器异步通知路径', '2023-12-26 15:44:20',
        '2023-12-26 15:44:20', 'yongoe', 'yongoe'),
       (21, 'alipay-returnUrl', 'http://localhost:8080/alipay/success', '页面跳转同步路径', '2023-12-26 15:44:52',
        '2023-12-26 15:44:52', 'yongoe', 'yongoe'),
       (22, 'alipay-signType', 'RSA2', '签名方式', '2023-12-26 15:45:16', '2023-12-26 15:45:16', 'yongoe', 'yongoe'),
       (23, 'alipay-charset', 'utf-8', '字符编码格式', '2023-12-26 15:45:37', '2023-12-26 15:45:37', 'yongoe',
        'yongoe'),
       (24, 'alipay-gatewayUrl', 'https://openapi-sandbox.dl.alipaydev.com/gateway.do', '支付宝网关',
        '2023-12-26 15:48:43', '2023-12-26 15:48:43', 'yongoe', 'yongoe'),
       (25, 'alipay-format', 'json', '格式', '2023-12-26 15:49:03', '2023-12-26 15:49:03', 'yongoe', 'yongoe');



DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint                                                       NOT NULL COMMENT '父id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名',
    `type`        int                                                          NOT NULL COMMENT '类型',
    `url`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接口路径',
    `component`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件位置',
    `icon`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
    `sort`        int                                                          NOT NULL COMMENT '顺序',
    `keep_alive`  tinyint(1)                                                   NOT NULL COMMENT '是否缓存',
    `is_show`     tinyint(1)                                                   NOT NULL COMMENT '是否显示',
    `enabled`     tinyint(1)                                                   NOT NULL COMMENT '是否启用',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


insert into `sys_menu`(`id`, `parent_id`, `name`, `type`, `url`, `component`, `icon`, `sort`, `keep_alive`, `is_show`,
                       `enabled`,
                       `create_time`, `update_time`, `create_by`, `update_by`)
values (1, 0, '系统管理', 1, '', '', 'fa fa-cog', 0, 0, 1, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (2, 0, '基础信息', 1, '', '', 'fa fa-table', 0, 0, 1, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe',
        'yongoe'),
       (3, 1, '用户管理', 2, '/system/user/**', '/system/User', '', 0, 0, 1, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (4, 1, '角色管理', 2, '/system/role/**', '/system/Role', '', 0, 0, 1, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (5, 1, '菜单管理', 2, '/system/menu/**', '/system/Menu', '', 0, 0, 1, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (6, 1, '系统配置', 2, '/system/config/**', '/system/Config', '', 0, 0, 1, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (7, 2, '部门', 2, '/basic/department/**', '/basic/Department', '', 0, 0, 1, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (8, 2, '职位', 2, '/basic/position/**', '/basic/Position', '', 0, 0, 1, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (9, 2, '数据字典', 2, '/basic/dict/**', '/basic/Dict', '', 0, 0, 1, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00',
        'yongoe', 'yongoe'),
       (10, 2, '数据字典-数据', 2, '/basic/dict/data/**', '/basic/DictData', '', 0, 0, 0, 1, '2023-01-01 00:00:00',
        '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (11, 1, '系统日志', 2, '/system/log/**', '/system/Log', '', 0, 0, 1, 1, '2023-09-19 05:51:08',
        '2023-09-19 05:51:08',
        'yongoe', 'yongoe');



DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `code`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色代码',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '中文名',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/*Data for the table `sys_role` */

insert into `sys_role`(`id`, `code`, `name`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, 'admin', '超级管理员', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe'),
       (2, 'register', '新用户', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe');


DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `role_id`     bigint                                                       NOT NULL COMMENT '角色id',
    `menu_id`     bigint                                                       NOT NULL COMMENT '菜单id',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user`
(
    `id`            bigint unsigned                                               NOT NULL AUTO_INCREMENT,
    `department_id` bigint                                                       DEFAULT NULL COMMENT '部门id',
    `position_id`   bigint                                                       DEFAULT NULL COMMENT '职位id',
    `username`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '账号',
    `password`      varchar(50) COLLATE utf8mb4_unicode_ci                        NOT NULL COMMENT '密码',
    `name`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '姓名',
    `avatar`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
    `email`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '邮箱',
    `phone`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '联系电话',
    `remark`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `last_ip`       varchar(50) COLLATE utf8mb4_unicode_ci                       DEFAULT NULL COMMENT '上次登录ip',
    `last_time`     datetime                                                     DEFAULT NULL COMMENT '上次登录时间',
    `enabled`       tinyint(1)                                                    NOT NULL COMMENT '是否启用',
    `create_time`   datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time`   datetime                                                      NOT NULL COMMENT '修改时间',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '创建人',
    `update_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`) COMMENT '用户名唯一'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


insert into `sys_user`(`id`, `department_id`, `position_id`, `username`, `password`, `name`, `avatar`, `email`, `phone`,
                       `remark`, `last_ip`,
                       `last_time`, `enabled`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, 1, 1, 'admin', '111111', 'yongoe',
        'https://img.xjh.me/random_img.php', '121887765@qq.com', '10086',
        '无', '127.0.0.1', '2023-01-01 00:00:00', 1,
        '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe');


DROP TABLE IF EXISTS `sys_user_auths`;

CREATE TABLE `sys_user_auths`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id`      bigint                                                        NOT NULL COMMENT '用户id',
    `login_type`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '登陆类型',
    `openid`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标识',
    `access_token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '凭据',
    `create_time`  datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time`  datetime                                                      NOT NULL COMMENT '修改时间',
    `create_by`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '创建人',
    `update_by`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `user_id`     bigint                                                       NOT NULL COMMENT '用户id',
    `role_id`     bigint                                                       NOT NULL COMMENT '角色id',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


insert into `sys_user_role`(`id`, `user_id`, `role_id`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, 1, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'yongoe', 'yongoe');

DROP TABLE IF EXISTS `basic_letter`;

CREATE TABLE `basic_letter`
(
    `id`           bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `addresser_id` bigint                                                       NOT NULL COMMENT '发件人id',
    `addresser`    varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '发件人',
    `addressee_id` bigint                                                       NOT NULL COMMENT '收件人id',
    `addressee`    varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '收件人',
    `title`        varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
    `content`      longtext COLLATE utf8mb4_unicode_ci                          NOT NULL COMMENT '内容',
    `state`        tinyint(1)                                                   NOT NULL COMMENT '状态',
    `create_time`  datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time`  datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`    varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`    varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log`
(
    `id`          bigint unsigned                        NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '用户',
    `type`        varchar(50) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '类型',
    `title`       varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
    `details`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '详情',
    `create_time` datetime                               NOT NULL COMMENT '创建时间',
    `update_time` datetime                               NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
