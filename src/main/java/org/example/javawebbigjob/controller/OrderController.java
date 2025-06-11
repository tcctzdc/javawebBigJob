package org.example.javawebbigjob.controller;

import org.example.javawebbigjob.entity.Order;
import org.example.javawebbigjob.entity.User;
import org.example.javawebbigjob.service.CreateOrderRequest;
import org.example.javawebbigjob.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            int offset = (page - 1) * size;
            List<Order> orders = orderService.findByPage(offset, size);
            int total = orderService.countAll();

            Map<String, Object> response = new HashMap<>();
            response.put("orders", orders);
            response.put("total", total);
            response.put("currentPage", page);
            response.put("pageSize", size);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching orders", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        try {
            logger.info("Fetching order with ID: {}", id);
            Order order = orderService.findById(id);
            if (order != null) {
                return ResponseEntity.ok(order);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error fetching order with ID: " + id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getByUserId(@PathVariable Long userId) {
        try {
            logger.info("Fetching orders for user ID: {}", userId);
            List<Order> orders = orderService.findByUserId(userId);
            logger.info("Found {} orders for user ID: {}", orders.size(), userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders for user ID: " + userId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody CreateOrderRequest request) {
        try {
            logger.info("Adding new order for user ID: {}", request.getOrder().getUserId());
            orderService.add(request.getOrder(), request.getItems());
            return ResponseEntity.ok("订单创建成功");
        } catch (Exception e) {
            logger.error("Error adding order", e);
            return ResponseEntity.status(500).body("创建订单失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Order order) {
        try {
            logger.info("Updating order ID: {}", id);
            order.setId(id);
            orderService.update(order);
            return ResponseEntity.ok("订单更新成功");
        } catch (Exception e) {
            logger.error("Error updating order", e);
            return ResponseEntity.status(500).body("更新订单失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            logger.info("正在删除订单 ID: {}", id);
            orderService.delete(id);
            return ResponseEntity.ok("订单删除成功");
        } catch (IllegalArgumentException e) {
            logger.warn("删除订单请求无效: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("删除订单 ID: {} 时发生错误", id, e);
            return ResponseEntity.status(500).body("删除订单失败: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> search(
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String status) {
        try {
            logger.info("Searching orders with orderNo: {}, userId: {}, status: {}", orderNo, userId, status);
            Long userIdLong = null;
            if (userId != null && !userId.trim().isEmpty()) {
                try {
                    userIdLong = Long.parseLong(userId);
                } catch (NumberFormatException e) {
                    logger.warn("Invalid userId format: {}", userId);
                    return ResponseEntity.badRequest().build();
                }
            }
            List<Order> orders = orderService.search(orderNo, userIdLong, status);
            logger.info("Found {} orders matching the criteria", orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error searching orders", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<?> getOrderUser(@PathVariable Long id) {
        try {
            logger.info("Fetching user information for order ID: {}", id);
            Order order = orderService.findById(id);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            User user = orderService.getOrderUser(order.getUserId());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error fetching user information for order ID: " + id, e);
            return ResponseEntity.internalServerError().body("获取用户信息失败: " + e.getMessage());
        }
    }
}