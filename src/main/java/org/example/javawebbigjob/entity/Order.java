package org.example.javawebbigjob.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Order {
    private Long id;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("total_price")
    private Double totalPrice;

    private String status;

    public Order(Long id, String orderNo, Long userId, String createTime, Double totalPrice, String status) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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
}
