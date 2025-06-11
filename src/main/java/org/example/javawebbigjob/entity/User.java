package org.example.javawebbigjob.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    
    @JsonProperty("real_name")
    private String realName;
    
    private String phone;
    private String address;
    
    @JsonProperty("create_time")
    private String createTime;

    public User() {
        this.username = "";
        this.password = "";
        this.realName = "";
        this.phone = "";
        this.address = "";
    }

    public User(Long id, String username, String realName, String phone, String address) {
        this.id = id;
        this.username = username != null ? username : "";
        this.password = "";
        this.realName = realName != null ? realName : "";
        this.phone = phone != null ? phone : "";
        this.address = address != null ? address : "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username != null ? username : "";
    }

    public void setUsername(String username) {
        this.username = username != null ? username : "";
    }

    public String getPassword() {
        return password != null ? password : "";
    }

    public void setPassword(String password) {
        this.password = password != null ? password : "";
    }

    public String getRealName() {
        return realName != null ? realName : "";
    }

    public void setRealName(String realName) {
        this.realName = realName != null ? realName : "";
    }

    public String getPhone() {
        return phone != null ? phone : "";
    }

    public void setPhone(String phone) {
        this.phone = phone != null ? phone : "";
    }

    public String getAddress() {
        return address != null ? address : "";
    }

    public void setAddress(String address) {
        this.address = address != null ? address : "";
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
