package com.yongoe.ecy.service;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.yongoe.ecy.entity.GeneratorConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动生成
 * 在 vue文件夹会有 sql ，需要手动执行
 *
 * @author yongoe
 * @since 2023/1/1
 */
public class GeneratorService {
    /**
     * sql必备字段: create_time, update_time, create_by, update_by
     */
    public static void main(String[] args) {
        GeneratorConfig generatorConfig = GeneratorConfig.builder()
                .url("jdbc:mysql://localhost:3306/ecy?serverTimezone=GMT%2B8&characterEncoding=utf8&useUnicode=true&useSSL=false")
                .projectPath(System.getProperty("user.dir"))
                .username("root")
                .password("123456")
                .author("yongoe")
                .tablePrefix("sys_")
                .tableName("sys_user")
                .packageName("system")
                .moduleName("部门")
                .parentId("1")
                .add(true)
                .update(true)
                .get(true)
                .delete(true)
                .upload(false)
                .export(false).build();
        GeneratorService.run(generatorConfig);
    }

    public static void run(GeneratorConfig config) {

        FastAutoGenerator.create(config.getUrl(), config.getUsername(), config.getPassword())
                //全局配置
                .globalConfig(builder -> {
                    builder.author(config.getAuthor())
                            .outputDir(config.getProjectPath() + "/ecy-server/src/main/java")
                            .disableOpenDir();
                })
                //包名配置
                .packageConfig(builder -> {
                    Map<OutputFile, String> map = new HashMap<>();
                    map.put(OutputFile.xml, config.getProjectPath() + "/ecy-server/src/main/resources/mapper/" + config.getPackageName());
                    builder.parent("com.yongoe.ecy").pathInfo(map).moduleName(config.getPackageName());
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(config.getTableName())
                            .addTablePrefix(config.getTablePrefix())
                            .serviceBuilder().formatServiceFileName("%sService");
                })
                .templateConfig(builder -> {
                    builder.entity("/templates/entity.java")
                            .service("/templates/service.java")
                            .serviceImpl("/templates/serviceImpl.java")
                            .mapper("/templates/mapper.java")
                            .xml("/templates/mapper.xml")
                            .controller("/templates/controller.java");
                })
                .injectionConfig(builder -> {
                    Map<String, Object> value = new HashMap<>();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    String directory = simpleDateFormat.format(new Date());
                    value.put("createTime", directory);
                    value.put("packageName", config.getPackageName());
                    value.put("moduleName", config.getModuleName());
                    value.put("parentId", config.getParentId());
                    value.put("add", config.getAdd());
                    value.put("update", config.getUpdate());
                    value.put("delete", config.getDelete());
                    value.put("get", config.getGet());
                    value.put("upload", config.getUpload());
                    value.put("export", config.getExport());
                    builder.customMap(value)
                            .customFile(new CustomFile.Builder().fileName(".sql")
                                    .templatePath("/templates/mysql.sql.ftl")
                                    .filePath(config.getProjectPath() + "/ecy-ui/src/views")
                                    .packageName(config.getPackageName()).build())
                            .customFile(new CustomFile.Builder().fileName(".vue")
                                    .templatePath("/templates/index.vue.ftl")
                                    .filePath(config.getProjectPath() + "/ecy-ui/src/views")
                                    .packageName(config.getPackageName()).build());
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }


}
