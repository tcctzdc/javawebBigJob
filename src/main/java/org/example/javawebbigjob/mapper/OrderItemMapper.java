package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.javawebbigjob.entity.OrderItem;
import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> findByOrderId(Long orderId);
    void insert(OrderItem item);
    void deleteByOrderId(Long orderId);
    List<OrderItem> findAll();
    void update(OrderItem item);
    void delete(Long id);
}
