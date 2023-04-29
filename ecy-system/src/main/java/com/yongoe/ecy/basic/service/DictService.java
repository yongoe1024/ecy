package com.yongoe.ecy.basic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.basic.entity.Dict;

/**
 * 数据字典
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface DictService extends IService<Dict> {

    Page<Dict> getDictByPage(Page<Dict> page, Dict dict);

}
