package org.example.javawebbigjob.entity;

import lombok.Data;

@Data
public class Delivery {
    private Long id;
    private Long orderId;
    private String company;
    private String trackingNumber;
    private String status;
    private String deliveryMan;

    public Delivery(Long id, Long orderId, String company, String trackingNumber, String status, String deliveryMan) {
        this.id = id;
        this.orderId = orderId;
        this.company = company;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.deliveryMan = deliveryMan;
    }

    public Delivery() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
}
