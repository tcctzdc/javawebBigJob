package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Order;
import org.example.javawebbigjob.entity.OrderItem;
import org.example.javawebbigjob.mapper.OrderItemMapper;
import org.example.javawebbigjob.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired private OrderItemMapper itemMapper;

    public List<Order> listAll() { return orderMapper.findAll(); }
    public void add(Order o, List<OrderItem> items) {
        orderMapper.insert(o);
        for (OrderItem i : items) {
            i.setOrderId(o.getId());
            itemMapper.insert(i);
        }
    }
    public void delete(Long id) {
        itemMapper.deleteByOrderId(id);
        orderMapper.delete(id);
    }
}
