package org.example.javawebbigjob.controller;

import org.example.javawebbigjob.entity.User;
import org.example.javawebbigjob.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin // 允许跨域请求（前后端分离时需要）
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // 获取用户列表（带分页）
    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            int offset = (page - 1) * size;
            List<User> users = userService.findByPage(offset, size);
            int total = userService.countAll();

            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            response.put("total", total);
            response.put("currentPage", page);
            response.put("pageSize", size);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 搜索用户
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String id) {
        try {
            logger.info("Searching users with username: {}, id: {}", username, id);
            List<User> users = userService.searchUsers(username, id);
            logger.info("Found {} users", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error searching users: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据ID获取单个用户
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody User user) {
        try {
            logger.info("Adding new user: {}", user.getUsername());
            userService.add(user);
            return ResponseEntity.ok("用户添加成功");
        } catch (Exception e) {
            logger.error("Error adding user", e);
            return ResponseEntity.status(500).body("添加用户失败: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody User user) {
        try {
            logger.info("Updating user ID: {}", user.getId());
            userService.update(user);
            return ResponseEntity.ok("用户更新成功");
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.status(500).body("更新用户失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            logger.info("Deleting user ID: {}", id);
            userService.delete(id);
            return ResponseEntity.ok("用户删除成功");
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            return ResponseEntity.status(500).body("删除用户失败: " + e.getMessage());
        }
    }

    @PostMapping("/batchDelete")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> ids) {
        try {
            userService.deleteBatch(ids);
            return ResponseEntity.ok("批量删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("批量删除失败: " + e.getMessage());
        }
    }

}
