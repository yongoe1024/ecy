package com.yongoe.ecy.basic.service.impl;

import com.yongoe.ecy.basic.controller.vo.req.FileChunkReq;
import com.yongoe.ecy.basic.controller.vo.res.FileChunkRes;
import com.yongoe.ecy.basic.service.UploadService;
import com.yongoe.ecy.utils.RedisUtils;
import com.yongoe.ecy.utils.UserUtils;
import jakarta.annotation.Resource;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * 断点续传
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Resource
    private RedisUtils redisUtils;
    @Value("${ecy.file-save-path}")
    private String fileSavePath;

    /**
     * 检查文件是否存在,不存在就返回需要上传的分片集合
     */
    @Override
    public FileChunkRes checkChunkExist(FileChunkReq chunkReq) {
        //检查文件是否已上传过，判断md5和文件大小是否一致
        boolean exist = checkFileExist(chunkReq);
        if (exist) {
            return new FileChunkRes(true);
        }
        //提前创建文件夹
        String chunkFileFolderPath = getChunkFilePath(chunkReq.getMd5());
        File chunkFileFolder = new File(chunkFileFolderPath);
        if (!chunkFileFolder.exists()) {
            if (!chunkFileFolder.mkdirs()) {
                throw new RuntimeException("无法创建文件夹" + chunkFileFolderPath);
            }
        }
        //检查分片是否已上传过
        Map<Object, Object> map = redisUtils.getMap(chunkReq.getMd5());
        if (map == null || map.size() == 0) {
            return new FileChunkRes(false, Set.of());
        }
        Set<Integer> uploaded = (Set<Integer>) map.get("uploaded");
        return new FileChunkRes(false, uploaded);
    }


    /**
     * 上传分片,记录redis
     */
    @Override
    public void uploadChunk(FileChunkReq chunkReq) throws IOException {
        //分块的目录
        String chunkFileFolderPath = getChunkFilePath(chunkReq.getMd5());
        //写入分片
        InputStream inputStream = chunkReq.getFile().getInputStream();
        OutputStream outputStream = new FileOutputStream(
                Path.of(chunkFileFolderPath, chunkReq.getChunkNumber() + "").toString());
        IOUtils.copy(inputStream, outputStream);
        //将该分片写入redis
        saveToRedis(chunkReq);
    }


    @Override
    public boolean mergeChunk(FileChunkReq chunkReq) {
        String chunkFilePath = getChunkFilePath(chunkReq.getMd5());
        String filePath = getFilePath(chunkReq.getFileName(), chunkReq.getMd5());
        // 检查分片是否都存在
        if (!checkChunks(chunkReq.getMd5(), chunkReq.getTotalChunks())) {
            return false;
        }
        File chunkFileFolder = new File(chunkFilePath);
        File mergeFile = new File(filePath);
        File[] chunks = chunkFileFolder.listFiles();
        // 切片排序1、2、3...
        List<File> fileList = Arrays.asList(chunks);
        fileList.sort(Comparator.comparingInt((File file) -> Integer.parseInt(file.getName())));
        // 开始合并
        try {
            RandomAccessFile randomAccessFileWriter = new RandomAccessFile(mergeFile, "rw");
            byte[] bytes = new byte[2048];
            for (File chunk : chunks) {
                RandomAccessFile randomAccessFileReader = new RandomAccessFile(chunk, "r");
                int len;
                while ((len = randomAccessFileReader.read(bytes)) != -1) {
                    randomAccessFileWriter.write(bytes, 0, len);
                }
                randomAccessFileReader.close();
            }
            randomAccessFileWriter.close();
            //删除分片
            chunkFileFolder = new File(chunkFilePath);
            FileUtils.deleteDirectory(chunkFileFolder);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分片写入Redis
     * 判断切片是否已存在，如果未存在，则创建基础信息，并保存。
     */
    private synchronized void saveToRedis(FileChunkReq chunkReq) {
        Map<Object, Object> map = redisUtils.getMap(chunkReq.getMd5());
        if (map == null || map.size() == 0) {
            map = new HashMap<>();
            map.put("totalChunks", chunkReq.getTotalChunks());
            map.put("totalSize", chunkReq.getTotalSize());
            map.put("fileName", chunkReq.getFileName());
            map.put("filePath", getChunkFilePath(chunkReq.getMd5()));
        }
        Set<Integer> uploaded = (Set<Integer>) map.get("uploaded");
        if (uploaded == null || uploaded.size() == 0) {
            uploaded = new HashSet<>();
            map.put("uploaded", uploaded);
        }
        uploaded.add(chunkReq.getChunkNumber());
        redisUtils.setMap(chunkReq.getMd5(), map);
    }

    /**
     * 检查分片是否都存在
     */
    private boolean checkChunks(String md5, int totalChunks) {
        Set<Integer> notExist = new HashSet<>();
        try {
            for (int i = 0; i < totalChunks; i++) {
                File file = new File(Path.of(getChunkFilePath(md5), String.valueOf(i)).toString());
                if (!file.exists())
                    notExist.add(i);
            }
            if (notExist.size() > 0)
                throw new Exception("分片不存在" + notExist);
        } catch (Exception e) {
            //检测redis中的分片信息并修复
            Map<Object, Object> map = redisUtils.getMap(md5);
            if (map != null && map.size() > 0) {
                Set<Integer> uploaded = (Set<Integer>) map.get("uploaded");
                //从redis中删除不存在的分片
                uploaded.removeAll(notExist);
                redisUtils.setMap(md5, map);
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断文件是否已经上传过
     */
    private boolean checkFileExist(FileChunkReq chunkReq) {
        String filePath = getFilePath(chunkReq.getFileName(), chunkReq.getMd5());
        File file = new File(filePath);
        if (file.exists()) {
            //判断大小是否一致
            return file.length() == chunkReq.getTotalSize();
        }
        return false;
    }

    /**
     * 得到文件的绝对路径
     */
    private String getFilePath(String fileName, String md5) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = md5 + suffix;
        return Path.of(getFullFilePath(), fileName).toString();
    }

    /**
     * 得到分块文件保存的目录
     */
    private String getChunkFilePath(String md5) {
        return Path.of(getFullFilePath(), "chunks", md5).toString();
    }

    /**
     * 得到完整文件保存的目录
     */
    private String getFullFilePath() {
        Long userId = UserUtils.getUserId();
        return Path.of(fileSavePath, String.valueOf(userId)).toString();
    }


}