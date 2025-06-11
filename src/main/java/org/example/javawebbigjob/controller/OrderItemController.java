package org.example.javawebbigjob.controller;

import org.example.javawebbigjob.entity.OrderItem;
import org.example.javawebbigjob.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService orderItemService;

//     获取所有订单项
    @GetMapping
    public ResponseEntity<List<OrderItem>> list() {
        try {
            logger.info("Fetching all order items");
            List<OrderItem> items = orderItemService.listAll();
            logger.info("Found {} order items", items.size());
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("Error fetching order items", e);
            return ResponseEntity.internalServerError().build();
        }
    }


//      根据订单ID获取订单项
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getByOrderId(@PathVariable Long orderId) {
        try {
            logger.info("Fetching order items for order ID: {}", orderId);
            List<OrderItem> items = orderItemService.findByOrderId(orderId);
            logger.info("Found {} order items for order ID: {}", items.size(), orderId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("Error fetching order items for order ID: " + orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }


// 添加订单项

    @PostMapping
    public ResponseEntity<String> add(@RequestBody OrderItem item) {
        try {
            logger.info("Adding new order item for order ID: {}", item.getOrderId());
            orderItemService.add(item);
            return ResponseEntity.ok("订单项添加成功");
        } catch (Exception e) {
            logger.error("Error adding order item", e);
            return ResponseEntity.status(500).body("添加订单项失败: " + e.getMessage());
        }
    }


//      更新订单项

    @PutMapping
    public ResponseEntity<String> update(@RequestBody OrderItem item) {
        try {
            logger.info("Updating order item ID: {}", item.getId());
            orderItemService.update(item);
            return ResponseEntity.ok("订单项更新成功");
        } catch (Exception e) {
            logger.error("Error updating order item", e);
            return ResponseEntity.status(500).body("更新订单项失败: " + e.getMessage());
        }
    }


//      删除订单项

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            logger.info("Deleting order item ID: {}", id);
            orderItemService.delete(id);
            return ResponseEntity.ok("订单项删除成功");
        } catch (Exception e) {
            logger.error("Error deleting order item", e);
            return ResponseEntity.status(500).body("删除订单项失败: " + e.getMessage());
        }
    }


//  删除指定订单的所有订单项
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteByOrderId(@PathVariable Long orderId) {
        try {
            logger.info("Deleting all order items for order ID: {}", orderId);
            orderItemService.deleteByOrderId(orderId);
            return ResponseEntity.ok("订单项批量删除成功");
        } catch (Exception e) {
            logger.error("Error deleting order items for order ID: " + orderId, e);
            return ResponseEntity.status(500).body("批量删除订单项失败: " + e.getMessage());
        }
    }


//    批量删除订单项
    @DeleteMapping("/batch")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> ids) {
        try {
            logger.info("Batch deleting order items: {}", ids);
            orderItemService.deleteBatch(ids);
            return ResponseEntity.ok("批量删除成功");
        } catch (Exception e) {
            logger.error("Error batch deleting order items", e);
            return ResponseEntity.status(500).body("批量删除失败: " + e.getMessage());
        }
    }
}
