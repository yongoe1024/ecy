package com.yongoe.ecy.basic.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yongoe.ecy.basic.entity.DictData;
import com.yongoe.ecy.basic.mapper.DictDataMapper;
import com.yongoe.ecy.basic.service.DictDataService;
import org.springframework.stereotype.Service;

/**
 * 数据字典-数据
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

    @Override
    public Page<DictData> getDictDataByPage(Page<DictData> page, DictData dictData) {
        return baseMapper.getDictDataByPage(page, dictData);
    }

}
