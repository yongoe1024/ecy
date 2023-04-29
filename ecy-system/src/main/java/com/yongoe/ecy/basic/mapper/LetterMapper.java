package com.yongoe.ecy.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.entity.Letter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 信件
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Mapper
public interface LetterMapper extends BaseMapper<Letter> {

    Page<Letter> getLetterByPage(Page<Letter> page, @Param("letter") Letter letter);

}
