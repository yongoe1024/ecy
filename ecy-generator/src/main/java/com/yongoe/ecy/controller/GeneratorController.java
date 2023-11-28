package com.yongoe.ecy.controller;

import com.yongoe.ecy.entity.GeneratorConfig;
import com.yongoe.ecy.service.GeneratorService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自动生成接口
 *
 * @author yongoe
 * @since 2023/1/1
 */
@RestController
public class GeneratorController {
    /**
     * sql必备字段: create_time, update_time, create_by, update_by
     * 在 vue文件夹会有 sql ，需要手动执行
     */
    /*
	   ALTER TABLE `ecy`.`basic_lettet` ADD COLUMN `create_time` DATETIME NOT NULL COMMENT '创建时间' ;
	   ALTER TABLE `ecy`.`basic_lettet` ADD COLUMN `update_time` DATETIME NOT NULL COMMENT '修改时间' ;
	   ALTER TABLE `ecy`.`basic_lettet` ADD COLUMN `create_by` VARCHAR(50) NOT NULL COMMENT '创建人' ;
	   ALTER TABLE `ecy`.`basic_lettet` ADD COLUMN `update_by` VARCHAR(50) NOT NULL COMMENT '修改人' ;
     */
    public static void main(String[] args) {
        GeneratorConfig generatorConfig = GeneratorConfig.builder()
                .url("jdbc:mysql://localhost:3306/ecy?serverTimezone=GMT%2B8&characterEncoding=utf8&useUnicode=true&useSSL=false")
                .projectPath(System.getProperty("user.dir"))
                .username("root")
                .password("123456")
                .author("yongoe")
                .tablePrefix("")
                .tableName("sys_role")
                .packageName("project") //  包名必填--新项目名
                .moduleName("信件管理")
                .parentId("1")
                .add(true)
                .update(true)
                .get(true)
                .delete(true)
                .upload(true)
                .export(true).build();
        GeneratorService.run(generatorConfig);
    }

}
