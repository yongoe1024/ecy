package com.yongoe.ecy.basic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yongoe.ecy.basic.entity.Position;

/**
 * 职位
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface PositionService extends IService<Position> {

    Page<Position> getPositionByPage(Page<Position> page, Position position);

}
