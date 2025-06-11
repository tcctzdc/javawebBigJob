package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.OrderItem;
import org.example.javawebbigjob.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;


//     查询所有订单项
    public List<OrderItem> listAll() {
        return orderItemMapper.findAll();
    }


//      根据订单ID查询订单项
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemMapper.findByOrderId(orderId);
    }


//      添加订单项
    @Transactional
    public void add(OrderItem item) {
        // 计算小计金额
        if (item.getUnitPrice() != null && item.getQuantity() != null) {
            item.setSubtotal(item.getUnitPrice() * item.getQuantity());
        }
        orderItemMapper.insert(item);
    }


//     更新订单项
    @Transactional
    public void update(OrderItem item) {
        // 如果更新了单价或数量，重新计算小计金额
        if (item.getUnitPrice() != null || item.getQuantity() != null) {
            OrderItem existingItem = orderItemMapper.findByOrderId(item.getOrderId())
                    .stream()
                    .filter(i -> i.getId().equals(item.getId()))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                Double unitPrice = item.getUnitPrice() != null ? item.getUnitPrice() : existingItem.getUnitPrice();
                Integer quantity = item.getQuantity() != null ? item.getQuantity() : existingItem.getQuantity();
                item.setSubtotal(unitPrice * quantity);
            }
        }
        orderItemMapper.update(item);
    }


//     删除订单项
    @Transactional
    public void delete(Long id) {
        orderItemMapper.delete(id);
    }


//     删除指定订单的所有订单项
    @Transactional
    public void deleteByOrderId(Long orderId) {
        orderItemMapper.deleteByOrderId(orderId);
    }


//    批量删除订单项
    @Transactional
    public void deleteBatch(List<Long> ids) {
        orderItemMapper.deleteBatch(ids);
    }
} 