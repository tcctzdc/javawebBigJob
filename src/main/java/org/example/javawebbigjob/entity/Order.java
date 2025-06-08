package org.example.javawebbigjob.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {
    private Long id;
    private Long userId;
    private Date createTime;
    private Double totalPrice;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order(Long id, Long userId, Date createTime, Double totalPrice, String status) {
        this.id = id;
        this.userId = userId;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order() {
    }
}
