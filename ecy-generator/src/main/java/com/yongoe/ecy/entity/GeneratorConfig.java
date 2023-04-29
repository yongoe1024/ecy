package com.yongoe.ecy.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 自动生成实体类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@Builder
public class GeneratorConfig {

    /**
     * 数据库url
     */
    private String url;
    /**
     * 数据库账号
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 作者
     */
    private String author;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 包名 ---例如： sys、basic等
     */
    private String packageName;
    /**
     * 模块名 ---中文即可
     */
    private String moduleName;
    /**
     * 菜单 父级id,sql相关
     */
    private String parentId;
    /**
     * 项目生成根目录
     */
    private String projectPath;
    /**
     * 是否在页面显示查询功能
     */
    private Boolean get;
    /**
     * 添加
     */
    private Boolean add;
    /**
     * 修改
     */
    private Boolean update;
    /**
     * 删除
     */
    private Boolean delete;
    /**
     * 导入
     */
    private Boolean upload;
    /**
     * 导出
     */
    private Boolean export;

}
