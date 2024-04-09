package com.yongoe.ecy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * 简单文件工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class FileUtils {
    private static String fileSavePath;
    //项目路径
    private static String contextPath;

    @Value("${server.servlet.context-path}")
    public void setContextPath(String s) {
        contextPath = s;
    }

    @Value("${ecy.file-save-path}")
    public void setFileSavePath(String s) {
        fileSavePath = s;
    }

    /**
     * 上传文件，返回文件url路径
     *
     * @param file 文件流
     * @return 文件url路径
     */
    public static String saveFile(MultipartFile file) {
        try {
            String fileName = getFileName(file);
            File dir = new File(getFullFilePath());
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new RuntimeException("无法创建文件夹");
                }
            }
            // 创建这个新文件
            File newFile = new File(Path.of(getFullFilePath(), fileName).toString());
            file.transferTo(newFile);
            LocalDate date = LocalDate.now();
            return contextPath + "/file/" + date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("无法创建文件");
        }
    }

    /**
     * 从MultipartFile得到新的随机文件名
     */
    public static String getFileName(MultipartFile file) {
        Objects.requireNonNull(file.getOriginalFilename());
        String suffix = getFileSuffix(file);
        return UUID.randomUUID().toString().replaceAll("-", "") + suffix;
    }

    /**
     * 从MultipartFile得到文件名后缀
     */
    public static String getFileSuffix(MultipartFile file) {
        Objects.requireNonNull(file.getOriginalFilename());
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    }


    /**
     * 保存文件的全路径
     */
    public static String getFullFilePath() {
        LocalDate date = LocalDate.now();
        return Path.of(fileSavePath, date.getYear() + "", date.getMonthValue() + "", date.getDayOfMonth() + "").toString();

    }

}
