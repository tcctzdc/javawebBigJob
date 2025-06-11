package org.example.javawebbigjob.controller;

import org.example.javawebbigjob.entity.Delivery;
import org.example.javawebbigjob.entity.Order;
import org.example.javawebbigjob.entity.User;
import org.example.javawebbigjob.service.DeliveryService;
import org.example.javawebbigjob.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private OrderService orderService;
    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            int offset = (page - 1) * size;
            List<Delivery> deliveries = deliveryService.findByPage(offset, size);
            int total = deliveryService.countAll();

            Map<String, Object> response = new HashMap<>();
            response.put("deliveries", deliveries);
            response.put("total", total);
            response.put("currentPage", page);
            response.put("pageSize", size);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching deliveries", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Delivery> getByOrderId(@PathVariable Long orderId) {
        try {
            logger.info("Fetching delivery for order ID: {}", orderId);
            Delivery delivery = deliveryService.findByOrderId(orderId);
            if (delivery != null) {
                return ResponseEntity.ok(delivery);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error fetching delivery for order ID: " + orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getById(@PathVariable Long id) {
        try {
            logger.info("Fetching delivery with ID: {}", id);
            Delivery delivery = deliveryService.findById(id);
            if (delivery != null) {
                return ResponseEntity.ok(delivery);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error fetching delivery with ID: " + id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> getDeliveryDetails(@PathVariable Long id) {
        try {
            logger.info("Fetching delivery details for ID: {}", id);
            Delivery delivery = deliveryService.findById(id);
            if (delivery == null) {
                return ResponseEntity.notFound().build();
            }

            // 获取订单信息
            Order order = orderService.findById(delivery.getOrderId());
            if (order == null) {
                return ResponseEntity.notFound().build();
            }

            // 获取用户信息
            User user = orderService.getOrderUser(order.getUserId());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // 构建响应数据
            Map<String, Object> response = new HashMap<>();
            
            // 添加配送信息
            Map<String, Object> deliveryInfo = new HashMap<>();
            deliveryInfo.put("id", delivery.getId());
            deliveryInfo.put("orderId", delivery.getOrderId());
            deliveryInfo.put("courierName", delivery.getCourierName() != null ? delivery.getCourierName() : "");
            deliveryInfo.put("company", delivery.getCompany() != null ? delivery.getCompany() : "");
            deliveryInfo.put("trackingNo", delivery.getTrackingNo() != null ? delivery.getTrackingNo() : "");
            deliveryInfo.put("status", delivery.getStatus() != null ? delivery.getStatus() : "");
            deliveryInfo.put("updateTime", delivery.getUpdateTime() != null ? delivery.getUpdateTime() : "");
            
            // 添加用户信息，使用更清晰的字段名，不包含创建时间
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("real_name", user.getRealName());
            userInfo.put("phone", user.getPhone());
            userInfo.put("address", user.getAddress());

            response.put("delivery", deliveryInfo);
            response.put("user", userInfo);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching delivery details for ID: " + id, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", "获取配送详情失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Delivery delivery) {
        try {
            logger.info("Adding new delivery for order ID: {}", delivery.getOrderId());
            delivery.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            deliveryService.add(delivery);
            return ResponseEntity.ok("运输记录创建成功");
        } catch (Exception e) {
            logger.error("Error adding delivery", e);
            return ResponseEntity.status(500).body("创建运输记录失败: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Delivery delivery) {
        try {
            logger.info("Received delivery update request: {}", delivery);
            deliveryService.update(delivery);
            logger.info("Delivery updated successfully");
            return ResponseEntity.ok("配送信息更新成功");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid delivery data: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating delivery: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新配送信息失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            logger.info("Deleting delivery ID: {}", id);
            deliveryService.delete(id);
            return ResponseEntity.ok("配送信息删除成功");
        } catch (Exception e) {
            logger.error("Error deleting delivery", e);
            return ResponseEntity.status(500).body("删除配送信息失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteByOrderId(@PathVariable Long orderId) {
        try {
            logger.info("Deleting delivery for order ID: {}", orderId);
            deliveryService.deleteByOrderId(orderId);
            return ResponseEntity.ok("配送信息删除成功");
        } catch (Exception e) {
            logger.error("Error deleting delivery for order ID: " + orderId, e);
            return ResponseEntity.status(500).body("删除配送信息失败: " + e.getMessage());
        }
    }
}