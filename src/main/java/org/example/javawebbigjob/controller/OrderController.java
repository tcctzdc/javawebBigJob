package org.example.javawebbigjob.controller;


import org.example.javawebbigjob.entity.Order;
import org.example.javawebbigjob.entity.OrderItem;
import org.example.javawebbigjob.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> list() {
        return orderService.listAll();
    }

    @PostMapping
    public void add(@RequestBody Order order, @RequestBody List<OrderItem> items) {
        orderService.add(order, items);
    }

    @PutMapping
    public void update(@RequestBody Order order) {
        // 由于OrderService中没有update方法，暂时注释掉
        // orderService.update(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}