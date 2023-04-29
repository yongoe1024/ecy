package com.yongoe.ecy.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.system.controller.vo.req.ConfigReqVo;
import com.yongoe.ecy.system.controller.vo.res.ConfigResVo;
import com.yongoe.ecy.system.convert.ConfigConvert;
import com.yongoe.ecy.system.entity.Config;
import com.yongoe.ecy.system.service.ConfigService;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import com.yongoe.ecy.utils.SysConfigUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "系统配置")
@RestController
@RequestMapping("/system/config")
public class ConfigController {
    @Resource
    private ConfigService configService;
    @Resource
    private SysConfigUtils configUtils;
    @Resource
    private ConfigConvert convert;

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size, @RequestBody ConfigReqVo reqVo) {
        Config entity = convert.req2Entity(reqVo);
        Page<Config> page = configService.getConfigByPage(Page.of(current, size), entity);
        Page<ConfigResVo> voPage = convert.entity2ResPage(page);
        return R.success().put(new PageUtils(voPage));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody ConfigReqVo reqVo) {
        Config config = convert.req2Entity(reqVo);
        configService.save(config);
        configUtils.init();
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody ConfigReqVo reqVo) {
        Config config = convert.req2Entity(reqVo);
        configService.updateById(config);
        configUtils.init();
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{ids}")
    public R delete(@PathVariable List<Long> ids) {
        configService.removeByIds(ids);
        configUtils.init();
        return R.success("删除成功");
    }

}