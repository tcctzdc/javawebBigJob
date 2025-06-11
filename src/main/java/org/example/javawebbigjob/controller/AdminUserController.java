package org.example.javawebbigjob.controller;

import org.apache.ibatis.jdbc.Null;
import org.example.javawebbigjob.service.AdminUserService;
import org.example.javawebbigjob.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class AdminUserController {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;
    @PostMapping
    public ResponseEntity<String> getPasswordByUsername(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        String password = adminUserService.getPasswordByUsername(username);
        if (password == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(password);
    }
}
