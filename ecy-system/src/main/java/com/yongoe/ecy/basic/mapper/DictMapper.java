package com.yongoe.ecy.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据字典
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    Page<Dict> getDictByPage(Page<Dict> page, @Param("dict") Dict dict);

}
