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
 *
 * @author yongoe
 * @since 2023/1/1
 */
public class GeneratorService {

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
                    String path = config.getProjectPath() + "/ecy-server/src/main/java/com/yongoe/ecy/" + config.getPackageName();
                    builder.customMap(value)
                            // convert
                            .customFile(new CustomFile.Builder().fileName("Convert.java")
                                    .templatePath("/templates/convert.java.ftl")
                                    .filePath(path + "/convert")
                                    .build())
                            // req
                            .customFile(new CustomFile.Builder().fileName("Req.java")
                                    .templatePath("/templates/req.java.ftl")
                                    .filePath(path + "/controller/vo/req")
                                    .build())
                            // res
                            .customFile(new CustomFile.Builder().fileName("Res.java")
                                    .templatePath("/templates/res.java.ftl")
                                    .filePath(path + "/controller/vo/res")
                                    .build())
                            // excel
                            .customFile(new CustomFile.Builder().fileName("Excel.java")
                                    .templatePath("/templates/excel.java.ftl")
                                    .filePath(path + "/controller/vo/excel")
                                    .build())
                            // sql
                            .customFile(new CustomFile.Builder().fileName(".sql")
                                    .templatePath("/templates/mysql.sql.ftl")
                                    .filePath(config.getProjectPath() + "/ecy-ui/src/views")
                                    .packageName(config.getPackageName()).build())
                            // vue
                            .customFile(new CustomFile.Builder().fileName(".vue")
                                    .templatePath("/templates/index.vue.ftl")
                                    .filePath(config.getProjectPath() + "/ecy-ui/src/views")
                                    .packageName(config.getPackageName()).build());
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }


}
