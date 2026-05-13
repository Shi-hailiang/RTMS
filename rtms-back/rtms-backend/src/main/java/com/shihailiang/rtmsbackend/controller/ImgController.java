package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@Tag(name = "Img", description = "图片相关接口")
public class ImgController {

    @Value("${img.root}")
    private String root;

    @PostMapping("/api/upload_img")
    @Operation(summary = "上传图片接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        log.info("UploadImg API is requested");
        if (file.isEmpty()) {
            return ResponseUtils.error(400, "上传图片为空");
        }

        try {
            // 获取上传文件的原始文件名
            String originalFileName = file.getOriginalFilename();

            log.info(originalFileName);

            // 构建上传目标路径
            String targetFilePath = root + File.separator + originalFileName;

            log.info(targetFilePath);

            // 保存文件
            file.transferTo(new File(targetFilePath));

            return ResponseUtils.success(originalFileName);
        } catch (IOException e) {
            return  ResponseUtils.error(500, "上传图片失败");
        }
    }

    @GetMapping("/api/download_img")
    @Operation(summary = "下载图片接口")
    @ApiOperationSupport(author = "石海良")
    public ResponseEntity<FileSystemResource> downloadImg(String img, HttpServletRequest request) {
        String filePath = root + "/" + img;
        log.info("请求的图片是：" + filePath);
        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 使用 FileSystemResource 包装文件并返回 ResponseEntity
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new FileSystemResource(file));
    }
}
