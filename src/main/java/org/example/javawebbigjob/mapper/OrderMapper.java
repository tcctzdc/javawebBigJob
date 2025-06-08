package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.javawebbigjob.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> findAll();
    Order findById(Long id);
    void insert(Order order);
    void update(Order order);
    void delete(Long id);
}

