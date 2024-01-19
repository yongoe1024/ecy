package com.yongoe.ecy.basic.controller.vo.res;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 断点续传
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
public class FileChunkRes {
    /**
     * 是否跳过上传
     */
    private Boolean skipUpload;

    /**
     * 已上传分片的集合
     */
    private Set<Integer> uploaded;

    public FileChunkRes(boolean skipUpload) {
        this.skipUpload = skipUpload;

    }

    public FileChunkRes(boolean skipUpload, Set<Integer> uploaded) {
        this.skipUpload = skipUpload;
        this.uploaded = uploaded;
    }
}