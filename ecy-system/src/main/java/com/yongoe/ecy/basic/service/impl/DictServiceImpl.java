package com.yongoe.ecy.basic.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.basic.entity.Dict;
import com.yongoe.ecy.basic.mapper.DictMapper;
import com.yongoe.ecy.basic.service.DictService;
import org.springframework.stereotype.Service;

/**
 * 数据字典
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public Page<Dict> getDictByPage(Page<Dict> page, Dict dict) {
        return baseMapper.getDictByPage(page, dict);
    }

}
