package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.PositionReq;
import com.yongoe.ecy.basic.controller.vo.res.PositionRes;
import com.yongoe.ecy.basic.convert.PositionConvert;
import com.yongoe.ecy.basic.entity.Position;
import com.yongoe.ecy.basic.service.PositionService;
import com.yongoe.ecy.config.aop.WebLog;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "职位")
@RestController
@RequestMapping("/basic/position")
public class PositionController {
    @Resource
    private PositionService positionService;
    @Resource
    private UserService userService;
    @Resource
    private PositionConvert positionConvert;

    //@WebLog
    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(@RequestBody PositionReq req) {
        Position entity = positionConvert.req2Entity(req);
        Page<Position> page = positionService.getPositionByPage(Page.of(req.getCurrent(), req.getSize()), entity);
        Page<PositionRes> resPage = positionConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(resPage));
    }

    @WebLog
    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody PositionReq req) {
        Position position = positionConvert.req2Entity(req);
        positionService.save(position);
        return R.success("添加成功");
    }

    @WebLog
    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody PositionReq req) {
        Position position = positionConvert.req2Entity(req);
        positionService.updateById(position);
        return R.success("修改成功");
    }

    @WebLog
    @Operation(summary = "删除数据")
    @PostMapping("/delete/{ids}")
    public R delete(@PathVariable List<Long> ids) {
        List<User> list = userService.list(new LambdaQueryWrapper<User>()
                .in(User::getPositionId, ids));
        if (!CollectionUtils.isEmpty(list)) {
            return R.error("该职位下存在用户，无法删除");
        }
        positionService.removeByIds(ids);
        return R.success("删除成功");
    }

}