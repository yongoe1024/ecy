CREATE DATABASE `ecy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `ecy`;

DROP TABLE IF EXISTS `basic_department`;

CREATE TABLE `basic_department`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint                                                       NOT NULL COMMENT '父id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门名称',
    `leader`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '负责人',
    `address`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系地址',
    `enabled`     tinyint(1)                                                   NOT NULL COMMENT '是否启用',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


insert into `basic_department`(`id`, `parent_id`, `name`, `leader`, `address`, `enabled`, `create_time`, `update_time`,
                               `create_by`, `update_by`)
values (1, 0, '总部', '老板', '无', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板');


DROP TABLE IF EXISTS `basic_dict`;

CREATE TABLE `basic_dict`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典名称',
    `type`        int                                                          NOT NULL COMMENT '字典类型(1下拉框,2单选)',
    `remark`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `basic_dict`(`id`, `name`, `type`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, '性别', 1, '无', '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板'),
       (2, '是否', 1, '无', '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板');

DROP TABLE IF EXISTS `basic_dict_data`;

CREATE TABLE `basic_dict_data`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `dict_id`     bigint                                                       NOT NULL COMMENT '字典类型id',
    `dict_key`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典键',
    `dict_value`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典值',
    `sort`        int                                                          NOT NULL COMMENT '字典顺序',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `basic_dict_data`(`id`, `dict_id`, `dict_key`, `dict_value`, `sort`, `create_time`, `update_time`,
                              `create_by`, `update_by`)
values (1, 1, '男', '1', 0, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板'),
       (2, 1, '女', '0', 0, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板'),
       (3, 2, '是', '1', 0, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板'),
       (4, 2, '否', '0', 0, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板');


DROP TABLE IF EXISTS `basic_position`;

CREATE TABLE `basic_position`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '职位',
    `enabled`     tinyint(1)                                                   NOT NULL COMMENT '是否启用',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `basic_position`(`id`, `name`, `enabled`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, 'CEO', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板');

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint                                                       NOT NULL COMMENT '父id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名',
    `type`        int                                                          NOT NULL COMMENT '类型',
    `url`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接口url(ant规则)',
    `component`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件位置',
    `icon`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
    `sort`        int                                                          NOT NULL COMMENT '顺序',
    `enabled`     tinyint(1)                                                   NOT NULL COMMENT '是否启用',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `sys_menu`(`id`, `parent_id`, `name`, `type`, `url`, `component`, `icon`, `sort`, `enabled`, `create_time`,
                       `update_time`, `create_by`, `update_by`)
values (1, 0, '基础信息', 1, '', '', 'fa fa-table', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板'),
       (2, 0, '系统管理', 1, '', '', 'fa fa-cog', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板'),
       (3, 1, '部门管理', 2, '', 'basic/Department', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',  '老板')
        ,
       (4, 3, '查', 3, '/basic/department/tree', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',   '老板')
        ,
       (5, 3, '增', 3, '/basic/department/add', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',   '老板')
        ,
       (6, 3, '改', 3, '/basic/department/update', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板')
        ,
       (7, 3, '删', 3, '/basic/department/delete/*', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板')
        ,
       (8, 1, '数据字典', 2, '', 'basic/Dict', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板'),
       (9, 8, '查', 3, '/basic/dict/page*', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板')
        ,
       (10, 8, '增', 3, '/basic/dict/add', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',   '老板')
        ,
       (11, 8, '改', 3, '/basic/dict/update', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',  '老板')
        ,
       (12, 8, '删', 3, '/basic/dict/delete/*', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',   '老板')
        ,
       (13, 8, '数据字典-数据', 3, '/basic/dict/data/**', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00',    '老板', '老板')
        ,
       (14, 1, '职位管理', 2, '', 'basic/Position', '', 0, 1, '2023-01-01 00:00:00',  '2023-01-01 00:00:00', '老板', '老板')
        ,
       (15, 14, '查', 3, '/basic/position/page*', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',  '老板')
        ,
       (16, 14, '增', 3, '/basic/position/add', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',    '老板')
        ,
       (17, 14, '改', 3, '/basic/position/update', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',   '老板')
        ,
       (18, 14, '删', 3, '/basic/position/delete/*', '', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00',  '老板', '老板')
        ,
       (19, 2, '菜单管理', 2, '/system/menu/**', 'system/Menu', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板')
        ,
       (20, 2, '角色管理', 2, '/system/role/**', 'system/Role', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00',  '老板', '老板')
        ,
       (21, 2, '用户管理', 2, '/system/user/**', 'system/User', '', 0, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00',      '老板', '老板');

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色',
    `name_zh`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色中文名',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `sys_role`(`id`, `name`, `name_zh`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, 'admin', '超级管理员', '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',
        '老板'),
       (2, 'register', '新用户', '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',
        '老板');

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `role_id`     bigint                                                       NOT NULL COMMENT '角色id',
    `menu_id`     bigint                                                       NOT NULL COMMENT '菜单id',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '姓名',
    `avatar`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
    `email`       varchar(50) COLLATE utf8mb4_unicode_ci                        NOT NULL COMMENT '邮箱',
    `phone`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '手机号',
    `remark`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `last_time`   datetime                                                     DEFAULT NULL COMMENT '上次登录时间',
    `enabled`     tinyint(1)                                                    NOT NULL COMMENT '是否启用',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL COMMENT '修改时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '创建人',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `sys_user`(`id`, `name`, `avatar`, `email`, `phone`, `remark`, `last_time`, `enabled`, `create_time`,
                       `update_time`, `create_by`, `update_by`)
values (1, '老板', 'https://gulimall-mrguo.oss-cn-beijing.aliyuncs.com/retouch_2022082218101242.jpg',
        '121887765@qq.com', '10086', '无', '2023-01-01 00:00:00', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00',
        '老板', '老板');


DROP TABLE IF EXISTS `sys_user_auths`;

CREATE TABLE `sys_user_auths`
(
    `id`            bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id`       bigint                                                        NOT NULL COMMENT '用户id',
    `identity_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '登陆类型',
    `identifier`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标识符',
    `credential`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '凭据',
    `create_time`   datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time`   datetime                                                      NOT NULL COMMENT '修改时间',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '创建人',
    `update_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `identifier` (`identifier`, `identity_type`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


insert into `sys_user_auths`(`id`, `user_id`, `identity_type`, `identifier`, `credential`, `create_time`, `update_time`,
                             `create_by`, `update_by`)
values (1, 1, 'username', 'admin', '123456', '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板',
        '老板');


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
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into `sys_user_role`(`id`, `user_id`, `role_id`, `create_time`, `update_time`, `create_by`, `update_by`)
values (1, 1, 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00', '老板', '老板');

