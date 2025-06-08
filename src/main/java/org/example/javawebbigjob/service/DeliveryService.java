package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Delivery;
import org.example.javawebbigjob.mapper.DeliveryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryMapper deliveryMapper;

    public List<Delivery> listAll() { return deliveryMapper.findAll(); }
    public void add(Delivery d) { deliveryMapper.insert(d); }
    public void update(Delivery d) { deliveryMapper.update(d); }
}
