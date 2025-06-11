package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Order;
import org.example.javawebbigjob.entity.OrderItem;
import org.example.javawebbigjob.entity.User;
import org.example.javawebbigjob.mapper.OrderItemMapper;
import org.example.javawebbigjob.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired 
    private OrderItemMapper itemMapper;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private UserService userService;


    public List<Order> findByPage(int offset, int size) {
        return orderMapper.findByPage(offset, size);
    }

    public int countAll() {
        return orderMapper.countAll();
    }
    public List<Order> listAll() {
        return orderMapper.findAll();
    }

    public Order findById(Long id) {
        return orderMapper.findById(id);
    }

    public User getOrderUser(Long userId) {
        return userService.findById(userId);
    }

    public List<Order> findByUserId(Long userId) {
        return orderMapper.findByUserId(userId);
    }

    @Transactional
    public void add(Order order, List<OrderItem> items) {
        // 计算订单总价
        double totalPrice = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
        order.setTotalPrice(Math.round(totalPrice * 100.0) / 100.0); // 保留两位小数

        // 保存订单
        orderMapper.insert(order);

        // 保存订单项
        for (OrderItem item : items) {
            item.setOrderId(order.getId());
            itemMapper.insert(item);
        }
    }

    @Transactional
    public void update(Order order) {
        orderMapper.update(order);
    }

    @Transactional
    public void delete(Long id) {
        try {
            // 1. 首先检查订单是否存在
            Order order = orderMapper.findById(id);
            if (order == null) {
                throw new IllegalArgumentException("订单不存在");
            }

            // 2. 删除配送记录(如果存在)
            try {
                deliveryService.deleteByOrderId(id);
            } catch (Exception e) {
                // 记录错误但继续删除订单
                System.err.println("删除配送记录时出错: " + e.getMessage());
            }

            // 3. 删除订单项
            itemMapper.deleteByOrderId(id);

            // 4. 最后删除订单
            orderMapper.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("删除订单失败: " + e.getMessage(), e);
        }
    }

    public List<Order> search(String orderNo, Long userId, String status) {
        return orderMapper.search(orderNo, userId, status);
    }
}

