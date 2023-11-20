package com.yongoe.ecy.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * excel工具类
 *
 * @author yongoe
 * @since 2023/1/1
 */
public class ExcelUtils {
    /**
     * 导入excel
     *
     * @param file      文件流
     * @param classname 实体类名
     */
    public static <T> List<T> upload(MultipartFile file, Class<T> classname) {
        List<T> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), classname, new ImportParams());
        } catch (Exception e) {
            throw new RuntimeException("解析excel失败");
        }
        list = list.stream().filter(t -> !ObjectUitls.isAllFieldNull(t)).toList();
        return list;
    }

    /**
     * 导出excel
     *
     * @param response  响应流
     * @param list      实体类集合
     * @param classname 实体类名
     */
    public static <T> void export(HttpServletResponse response, List<T> list, Class<T> classname) {
        // 设置excel 信息
        ExportParams params = new ExportParams(null, "sheet1", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, classname, list);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
        //下载文件无法获取Content-Disposition 解决办法
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-disposition");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("导出excel失败");
        }
    }
}
