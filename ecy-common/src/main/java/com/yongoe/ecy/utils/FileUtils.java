package com.yongoe.ecy.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class FileUtils {
    /**
     * 上传文件，返回文件url路径
     *
     * @param file    文件流
     * @param request 请求流
     * @return 文件url全路径
     */
    public static String getFileDirectory(MultipartFile file, HttpServletRequest request) {
        String fileSavePath = System.getProperty("user.dir") + "/ecy-file/";
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
        String directory = simpleDateFormat.format(new Date());
        // 如果目录不存在，则创建
        File dir = new File(fileSavePath + directory);
        if (!dir.exists()) {
            // 文件夹不存在
            if (!dir.mkdirs()) {
                //无法创建文件夹
                throw new RuntimeException("无法创建文件夹");
            }
        }
        // 创建这个新文件
        File newFile = new File(fileSavePath + directory + fileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new RuntimeException("无法创建文件");
        }
        // 创建文件
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file/" + directory + fileName;
    }

}
