package com.yongoe.ecy.system.controller.vo.res;

import com.yongoe.ecy.system.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRes {

    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 接口路径
     */
    private String url;
    /**
     * 组件位置
     */
    private String component;
    /**
     * 图标
     */
    private String icon;
    /**
     * 顺序
     */
    private Integer sort;
    /**
     * 是否缓存
     */
    private Boolean keepAlive;
    /**
     * 是否显示
     */
    private Boolean isShow;
    /**
     * 是否启用
     */
    private Boolean enabled;

    private List<Menu> children;
}