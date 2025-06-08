package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.javawebbigjob.entity.Delivery;

import java.util.List;

@Mapper
public interface DeliveryMapper {
    List<Delivery> findAll();
    Delivery findByOrderId(Long orderId);
    void insert(Delivery delivery);
    void update(Delivery delivery);
}
