package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Delivery;
import org.example.javawebbigjob.entity.User;
import org.example.javawebbigjob.mapper.DeliveryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryMapper deliveryMapper;

    public List<Delivery> findByPage(int offset, int size) {
        return deliveryMapper.findByPage(offset, size);
    }

    public int countAll() {
        return deliveryMapper.countAll();
    }

    public List<Delivery> listAll() {
        return deliveryMapper.findAll();
    }

    public Delivery findById(Long id) {
        return deliveryMapper.findById(id);
    }

    public Delivery findByOrderId(Long orderId) {
        return deliveryMapper.findByOrderId(orderId);
    }

    @Transactional
    public void add(Delivery delivery) {
        if (delivery == null) {
            throw new IllegalArgumentException("运输记录不能为空");
        }
        if (delivery.getOrderId() == null) {
            throw new IllegalArgumentException("订单ID不能为空");
        }
        if (delivery.getStatus() == null) {
            delivery.setStatus("pending"); // 设置默认状态
        }
        if (delivery.getUpdateTime() == null || delivery.getUpdateTime().trim().isEmpty()) {
            delivery.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        deliveryMapper.insert(delivery);
    }

    @Transactional
    public void update(Delivery delivery) {
        if (delivery == null) {
            throw new IllegalArgumentException("配送信息不能为空");
        }
        if (delivery.getId() == null) {
            throw new IllegalArgumentException("配送ID不能为空");
        }
        if (delivery.getCourierName() == null || delivery.getCourierName().trim().isEmpty()) {
            throw new IllegalArgumentException("快递员不能为空");
        }
        if (delivery.getTrackingNo() == null || delivery.getTrackingNo().trim().isEmpty()) {
            throw new IllegalArgumentException("运单号不能为空");
        }
        if (delivery.getCompany() == null || delivery.getCompany().trim().isEmpty()) {
            throw new IllegalArgumentException("快递公司不能为空");
        }
        
        // 设置更新时间
        delivery.setUpdateTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date()));
        
        deliveryMapper.update(delivery);
    }

    @Transactional
    public void delete(Long id) {
        deliveryMapper.delete(id);
    }

    @Transactional
    public void deleteByOrderId(Long orderId) {
        deliveryMapper.deleteByOrderId(orderId);
    }
}
