package com.yongoe.ecy.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * page工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
public class PageUtils {
    /**
     * 总记录数
     */
    private long total;
    /**
     * 每页记录数
     */
    private long size;
    /**
     * 当前页数
     */
    private long current;
    /**
     * 总页数
     */
    private long pages;
    /**
     * 列表数据
     */
    private List<?> list;

    /**
     * 分页
     */
    public PageUtils(List<?> list, long total, long size, long current, long pages) {
        this.list = list;
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = pages;
    }

    /**
     * 分页
     */
    public PageUtils(IPage<?> page) {
        this.list = page.getRecords();
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
    }

}
