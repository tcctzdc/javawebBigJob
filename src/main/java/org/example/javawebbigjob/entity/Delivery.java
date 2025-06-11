package org.example.javawebbigjob.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Delivery {
    private Long id;
    
    @JsonProperty("orderId")
    private Long orderId;
    
    @JsonProperty("courierName")
    private String courierName;
    
    private String company;
    
    @JsonProperty("trackingNo")
    private String trackingNo;
    
    private String status;
    
    @JsonProperty("updateTime")
    private String updateTime;

    public Delivery() {
    }

    public Delivery(Long id, Long orderId, String courierName, String company, String trackingNo, String status, String updateTime) {
        this.id = id;
        this.orderId = orderId;
        this.courierName = courierName;
        this.company = company;
        this.trackingNo = trackingNo;
        this.status = status;
        this.updateTime = updateTime;
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

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
