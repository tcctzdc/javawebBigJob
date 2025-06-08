package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.OrderItem;
import org.example.javawebbigjob.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;

    public List<OrderItem> listAll() { return orderItemMapper.findAll(); }
    public void add(OrderItem item) { orderItemMapper.insert(item); }
    public void update(OrderItem item) { orderItemMapper.update(item); }
    public void delete(Long id) { orderItemMapper.delete(id); }
} 