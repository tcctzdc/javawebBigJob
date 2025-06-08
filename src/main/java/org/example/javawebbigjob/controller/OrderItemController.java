package org.example.javawebbigjob.controller;


import org.example.javawebbigjob.entity.OrderItem;
import org.example.javawebbigjob.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItem> list() {
        return orderItemService.listAll();
    }

    @PostMapping
    public void add(@RequestBody OrderItem item) {
        orderItemService.add(item);
    }

    @PutMapping
    public void update(@RequestBody OrderItem item) {
        orderItemService.update(item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderItemService.delete(id);
    }
}
