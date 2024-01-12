package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.req.LetterReq;
import com.yongoe.ecy.basic.controller.vo.res.LetterRes;
import com.yongoe.ecy.basic.convert.LetterConvert;
import com.yongoe.ecy.basic.entity.Letter;
import com.yongoe.ecy.basic.service.LetterService;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.UserService;
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
import java.util.Objects;

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
        // 没读的数量
        long count = letterService.count(new LambdaQueryWrapper<Letter>()
                .eq(Letter::getState, false)
                .eq(Letter::getAddresseeId, UserUtils.getUserId()));
        // 最新6个
        Letter letter = new Letter();
        letter.setAddresseeId(UserUtils.getUserId());
        List<Letter> records = letterService.getLetterByPage(Page.of(1, 6), letter).getRecords();
        List<LetterReq> letterReqs = letterConvert.entity2ReqList(records);
        Map<String, Object> map = new HashMap<>();
        map.put("list", letterReqs);
        map.put("num", count);
        return R.success().put(map);
    }

    @Operation(summary = "查询信件详情")
    @PostMapping("/info")
    public R info(Long id) {
        Letter letter = letterService.getById(id);
        if (letter == null)
            return R.error("信件不存在");
        Long userId = UserUtils.getUserId();
        Long addresserId = letter.getAddresserId();
        Long addresseeId = letter.getAddresseeId();
        if (!Objects.equals(userId, addresserId) && !Objects.equals(userId, addresseeId))
            return R.error("信件不存在");
        //如果收件人是自己，就已读
        if (addresseeId.equals(userId)) {
            letter.setState(true);
            letterService.updateById(letter);
        }
        LetterReq letterReq = letterConvert.entity2Req(letter);
        return R.success().put(letterReq);
    }

    @Operation(summary = "查询分页数据-收件箱")
    @PostMapping("/recv")
    public R revc(@RequestBody LetterReq req) {
        Letter entity = letterConvert.req2Entity(req);
        Long userId = UserUtils.getUserId();
        entity.setAddresseeId(userId);
        Page<Letter> page = letterService.getLetterByPage(Page.of(req.getCurrent(), req.getSize()), entity);
        Page<LetterRes> resPage = letterConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(resPage));
    }

    @Operation(summary = "查询分页数据-发件箱")
    @PostMapping("/send")
    public R send(@RequestBody LetterReq req) {
        Letter entity = letterConvert.req2Entity(req);
        Long userId = UserUtils.getUserId();
        entity.setAddresserId(userId);
        Page<Letter> page = letterService.getLetterByPage(Page.of(req.getCurrent(), req.getSize()), entity);
        Page<LetterRes> resPage = letterConvert.entity2ResPage(page);
        return R.success().put(new PageUtils(resPage));
    }

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public R add(@RequestBody LetterReq req) {
        Letter letter = letterConvert.req2Entity(req);
        letter.setAddresser(UserUtils.getName());
        letter.setAddresserId(UserUtils.getUserId());
        letter.setState(false);
        letter.setContent(letter.getContent().trim());
        letterService.save(letter);
        return R.success("添加成功");
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public R update(@RequestBody LetterReq req) {
        Letter letter = letterConvert.req2Entity(req);
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