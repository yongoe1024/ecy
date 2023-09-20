package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.LetterReqVo;
import com.yongoe.ecy.basic.controller.vo.res.LetterResVo;
import com.yongoe.ecy.basic.convert.LetterConvert;
import com.yongoe.ecy.basic.entity.Letter;
import com.yongoe.ecy.basic.service.LetterService;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.UserService;
import com.yongoe.ecy.utils.Base64Utils;
import com.yongoe.ecy.utils.PageUtils;
import com.yongoe.ecy.utils.R;
import com.yongoe.ecy.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信件
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Tag(name = "信件管理")
@RestController
@RequestMapping("/basic/letter")
public class LetterController {
    @Resource
    private LetterService letterService;
    @Resource
    private LetterConvert letterConvert;
    @Resource
    private UserService userService;

    @Operation(summary = "查询用户列表")
    @PostMapping("/user")
    public R user() {
        List<User> list = userService.list();
        return R.success().put(list);
    }

    @Operation(summary = "查询最新数据6个")
    @PostMapping("/list")
    public R list() {
        Letter letter = new Letter();
        letter.setAddresseeId(UserUtils.getUserId());
        // 没读的数量
        List<Letter> list = letterService.list(new LambdaQueryWrapper<Letter>()
                .eq(Letter::getState, false)
                .eq(Letter::getAddresseeId, UserUtils.getUserId()));
        // 最新6个
        List<Letter> records = letterService.getLetterByPage(Page.of(1, 6), letter).getRecords();
        for (Letter record : records) {
            String base64Decode = Base64Utils.getBase64Decode(record.getContent());
            if (base64Decode.length() >= 15)
                base64Decode = base64Decode.substring(0, 15);
            record.setContent(base64Decode);
        }
        List<LetterReqVo> letterReqVos = letterConvert.entity2ReqList(records);
        Map<String, Object> map = new HashMap<>();
        map.put("list", letterReqVos);
        map.put("num", list.size());
        return R.success().put(map);
    }

    @Operation(summary = "查询信件详情")
    @PostMapping("/info")
    public R info(Long id) {
        Letter byId = letterService.getById(id);
        Long userId = UserUtils.getUserId();
        //如果收件人是自己，就已读
        if (byId.getAddresseeId().equals(userId)) {
            byId.setState(true);
            letterService.updateById(byId);
        }
        byId.setContent(Base64Utils.getBase64Decode(byId.getContent()));
        LetterReqVo letterReqVo = letterConvert.entity2Req(byId);
        return R.success().put(letterReqVo);
    }

    @Operation(summary = "查询分页数据")
    @PostMapping("/page")
    public R page(Long current, Long size, @RequestBody LetterReqVo reqVo) {
        Letter entity = letterConvert.req2Entity(reqVo);
        Page<Letter> page = letterService.getLetterByPage(Page.of(current, size), entity);
        Page<LetterResVo> voPage = letterConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(voPage));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody LetterReqVo reqVo) {
        Letter letter = letterConvert.req2Entity(reqVo);
        letter.setAddresser(UserUtils.getName());
        letter.setAddresserId(UserUtils.getUserId());
        letter.setState(false);
        String base64Encode = Base64Utils.getBase64Encode(letter.getContent().trim());
        letter.setContent(base64Encode);
        letterService.save(letter);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody LetterReqVo reqVo) {
        Letter letter = letterConvert.req2Entity(reqVo);
        letterService.updateById(letter);
        return R.success("修改成功");
    }

    @Operation(summary = "删除数据")
    @PostMapping("/delete/{ids}")
    public R delete(@PathVariable List<Long> ids) {
        letterService.removeByIds(ids);
        return R.success("删除成功");
    }
}