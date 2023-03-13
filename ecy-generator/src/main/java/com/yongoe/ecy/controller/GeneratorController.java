package com.yongoe.ecy.controller;

import com.yongoe.ecy.entity.GeneratorConfig;
import com.yongoe.ecy.service.GeneratorService;
import com.yongoe.ecy.utils.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自动生成接口
 *
 * @author yongoe
 * @since 2023/1/1
 */
@RestController
public class GeneratorController {
    @RequestMapping("/system/generator")
    public R run(@RequestBody GeneratorConfig config) {
        if ("auto".equals(config.getProjectPath())) {
            config.setProjectPath(System.getProperty("user.dir"));
        }
        GeneratorService.run(config);
        return R.success("生成成功");
    }

}
