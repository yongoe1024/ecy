package com.yongoe.ecy.basic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongoe.ecy.basic.controller.vo.res.DepartmentRes;
import com.yongoe.ecy.basic.controller.vo.res.DictDataRes;
import com.yongoe.ecy.basic.controller.vo.res.PositionRes;
import com.yongoe.ecy.basic.convert.DepartmentConvert;
import com.yongoe.ecy.basic.convert.DictDataConvert;
import com.yongoe.ecy.basic.convert.PositionConvert;
import com.yongoe.ecy.basic.entity.Department;
import com.yongoe.ecy.basic.entity.DictData;
import com.yongoe.ecy.basic.entity.Position;
import com.yongoe.ecy.basic.service.DepartmentService;
import com.yongoe.ecy.basic.service.DictService;
import com.yongoe.ecy.basic.service.PositionService;
import com.yongoe.ecy.system.controller.vo.res.RoleRes;
import com.yongoe.ecy.system.convert.RoleConvert;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.service.RoleService;
import com.yongoe.ecy.utils.FileUtils;
import com.yongoe.ecy.utils.R;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RestController
public class CommonController {
    @Value("${ecy.file-save-path}")
    private String fileSavePath;
    @Value("${server.servlet.context-path}")
    private String pathPrefix;
    @Resource
    private PositionService positionService;
    @Resource
    private PositionConvert positionConvert;
    @Resource
    private DictService dictService;
    @Resource
    private DictDataConvert dictDataConvert;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private DepartmentConvert departmentConvert;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleConvert roleConvert;

    /**
     * 查询所有角色
     */
    @PostMapping("/getRole")
    public R list() {
        Page<Role> list = roleService.getRoleByPage(Page.of(-1, -1), new Role());
        List<RoleRes> resList = roleConvert.entity2Res(list.getRecords());
        return R.success().put(resList);
    }

    /**
     * 查询可用部门
     */
    @PostMapping("/getDept")
    public R getDept() {
        List<Department> list = departmentService.getMenuByTree(new LambdaQueryWrapper<Department>()
                .eq(Department::getEnabled, true));
        List<DepartmentRes> resList = departmentConvert.entity2ResList(list);
        return R.success().put(resList);
    }

    /**
     * 查询可用字典
     */
    @PostMapping("/getDict")
    public R getDict(String name, String[] value) {
        List<DictData> dictData = dictService.getDict(name, value);
        List<DictDataRes> resList = dictDataConvert.entity2ResList(dictData);
        return R.success().put(resList);
    }

    /**
     * 查询可用职位
     */
    @PostMapping("/getPosition")
    public R getPosition() {
        List<Position> list = positionService.list(new LambdaQueryWrapper<Position>()
                .eq(Position::getEnabled, true));
        List<PositionRes> resList = positionConvert.entity2ResList(list);
        return R.success().put(resList);
    }

    /**
     * 普通下载文件,流式传输
     *
     * @param filePath 文件路径
     */
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(String filePath) throws IOException {
        String prefix = Path.of(pathPrefix, "file").toString();
        if (filePath.startsWith(prefix))
            filePath = filePath.substring(prefix.length());
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
    public R uploadFile(MultipartFile file) {
        String url = FileUtils.saveFile(file);
        return R.success("上传成功").put(url);
    }

    /**
     * 普通上传文件-批量
     *
     * @param file 文件数组
     * @return 文件url路径数组
     */
    @PostMapping("/upload/batch")
    public R uploadFile(List<MultipartFile> file) {
        List<String> urlList = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String url = FileUtils.saveFile(multipartFile);
            urlList.add(url);
        }
        return R.success("上传成功").put(urlList);
    }
}
