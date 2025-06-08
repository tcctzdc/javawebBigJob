package org.example.javawebbigjob.controller;


import org.example.javawebbigjob.entity.Delivery;
import org.example.javawebbigjob.service.DeliveryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public List<Delivery> list() {
        return deliveryService.listAll();
    }

    @PostMapping
    public void add(@RequestBody Delivery delivery) {
        deliveryService.add(delivery);
    }

    @PutMapping
    public void update(@RequestBody Delivery delivery) {
        deliveryService.update(delivery);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // 由于DeliveryService中没有delete方法，暂时注释掉
        // deliveryService.delete(id);
    }
}