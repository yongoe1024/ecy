package com.yongoe.ecy.basic.controller;

import com.yongoe.ecy.basic.controller.vo.req.FileChunkReq;
import com.yongoe.ecy.basic.controller.vo.res.FileChunkRes;
import com.yongoe.ecy.basic.service.UploadService;
import com.yongoe.ecy.utils.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 断点续传
 * 支持分片上传下载，秒传，但与 FileUtils 不一样的目录结构
 *
 * @author yongoe
 * @since 2023/1/1
 */
@RestController
@RequestMapping("/upload")
public class UploaderController {

    @Resource
    private UploadService uploadService;

    /**
     * 检查分片是否存在
     */
    @PostMapping("checkChunkExist")
    public R checkChunkExist(@RequestBody FileChunkReq chunkReq) {
        try {
            FileChunkRes res = uploadService.checkChunkExist(chunkReq);
            return R.success().put(res);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("检查分片失败");
        }
    }

    /**
     * 上传文件分片
     */
    @PostMapping("uploadChunk")
    public R uploadChunk(FileChunkReq chunkReq) {
        try {
            uploadService.uploadChunk(chunkReq);
            return R.success();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传分片失败");
        }
    }

    /**
     * 请求合并文件分片
     */
    @PostMapping("merge")
    public R mergeChunks(@RequestBody FileChunkReq chunkReq) {
        boolean success = uploadService.mergeChunk(chunkReq);
        if (success) {
            return R.success();
        }
        return R.error("合并失败");
    }

}