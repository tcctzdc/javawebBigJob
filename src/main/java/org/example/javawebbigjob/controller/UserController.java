package org.example.javawebbigjob.controller;

import org.example.javawebbigjob.entity.User;
import org.example.javawebbigjob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin // 允许跨域请求（前后端分离时需要）
public class UserController {

    @Autowired
    private UserService userService;

    // 获取用户
    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        int offset = (page -1) * size;
        List<User> users = userService.findByPage(offset, size);
        int total = userService.countAll();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("total", total);
        response.put("currentPage", page);
        response.put("pageSize", size);

        return ResponseEntity.ok(response);
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

    // 新增用户
    @PostMapping
    public ResponseEntity<String> add(@RequestBody User user) {
        try {
            userService.add(user);
            return ResponseEntity.ok("用户添加成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("添加用户失败: " + e.getMessage());
        }
    }

    // 更新用户
    @PutMapping
    public ResponseEntity<String> update(@RequestBody User user) {
        try {
            userService.update(user);
            System.out.println("用户更新成功");  // 这里打印到后端控制台
            return ResponseEntity.ok("用户更新成功");
        } catch (Exception e) {
            System.err.println("更新用户失败: " + e.getMessage());  // 错误打印到控制台
            return ResponseEntity.status(500).body("更新用户失败: " + e.getMessage());
        }
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("用户删除成功");
        } catch (Exception e) {
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
