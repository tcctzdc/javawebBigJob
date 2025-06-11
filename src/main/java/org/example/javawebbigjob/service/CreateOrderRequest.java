package org.example.javawebbigjob.service;

import lombok.Data;
import org.example.javawebbigjob.entity.Order;
import org.example.javawebbigjob.entity.OrderItem;

import java.util.List;

@Data
public class CreateOrderRequest {
    private Order order;
    private List<OrderItem> items;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
} 