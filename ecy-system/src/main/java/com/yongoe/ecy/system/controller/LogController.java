package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.res.LogResVo;
import com.yongoe.ecy.system.convert.LogConvert;
import com.yongoe.ecy.system.entity.Log;
import com.yongoe.ecy.system.service.LogService;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志
 *
 * @author yongoe
 * @since 2023/09/19
 */
@Tag(name = "系统日志")
@RestController
@RequestMapping("/system/log")
public class LogController {
    @Resource
    private LogService logService;
    @Resource
    private LogConvert logConvert;
    @Resource
    private UserService userService;

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size) {
        Page<Log> page = logService.getLogByPage(Page.of(current, size));
        Page<LogResVo> voPage = logConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(voPage));
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete")
    public R delete() {
        logService.remove(null);
        return R.success("删除成功");
    }

}