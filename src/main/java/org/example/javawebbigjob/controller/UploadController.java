package org.example.javawebbigjob.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UploadController {
    private static final String UPLOAD_DIR = "E:/Users/chentao/Desktop/javaweb/picture";
    private static final String SERVER_URL = "http://localhost:8081";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("请选择要上传的文件");
        }

        try {
            // 创建上传目录
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    return ResponseEntity.status(500).body("创建上传目录失败");
                }
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return ResponseEntity.badRequest().body("文件名不能为空");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;

            // 保存文件
            File destFile = new File(UPLOAD_DIR + File.separator + newFilename);
            file.transferTo(destFile);
            
            if (!destFile.exists()) {
                return ResponseEntity.status(500).body("文件保存失败");
            }

            // 返回完整的文件访问URL
            String fileUrl = SERVER_URL + "/picture/" + newFilename;
            
            // 创建响应数据
            Map<String, String> response = new HashMap<>();
            response.put("data", fileUrl);
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("文件上传失败：" + e.getMessage());
        }
    }
}
