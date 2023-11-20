package com.yongoe.ecy.basic.controller;

import com.yongoe.ecy.utils.FileUtils;
import com.yongoe.ecy.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用接口
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Controller
public class CommonController {
    @Value("${ecy.file-save-path}")
    private String fileSavePath;

    /**
     * 普通下载文件,流式传输
     *
     * @param filePath 文件路径
     */
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(String filePath) throws IOException {
        Path path = Path.of(fileSavePath, filePath);
        // 从文件系统中获取文件输入流
        File file = new File(path.toString());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    /**
     * 普通上传文件-单个
     *
     * @param file 文件
     * @return 文件url路径
     */
    @PostMapping("/upload/single")
    @ResponseBody
    public R uploadFile(MultipartFile file,String ss) {
        String url = FileUtils.saveFile(file);
        return R.success().put(url);
    }

    /**
     * 普通上传文件-批量
     *
     * @param file 文件数组
     * @return 文件url路径数组
     */
    @PostMapping("/upload/batch")
    @ResponseBody
    public R uploadFile(List<MultipartFile> file) {
        List<String> urlList = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String url = FileUtils.saveFile(multipartFile);
            urlList.add(url);
        }
        return R.success().put(urlList);
    }
}
