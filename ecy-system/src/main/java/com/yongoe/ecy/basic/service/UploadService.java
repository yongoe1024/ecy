package com.yongoe.ecy.basic.service;

import com.yongoe.ecy.basic.controller.vo.req.FileChunkReq;
import com.yongoe.ecy.basic.controller.vo.res.FileChunkRes;

import java.io.IOException;

/**
 * 断点续传
 *
 * @author yongoe
 * @since 2023/1/1
 */
public interface UploadService {

    /**
     * 检查文件是否存在，如果存在则跳过该文件的上传，如果不存在，返回需要上传的分片集合
     */
    FileChunkRes checkChunkExist(FileChunkReq chunkReq);

    /**
     * 上传文件分片
     */
    void uploadChunk(FileChunkReq chunkReq) throws IOException;

    /**
     * 合并文件分片
     */
    boolean mergeChunk(FileChunkReq chunkReq);
}