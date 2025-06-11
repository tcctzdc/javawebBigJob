package org.example.javawebbigjob.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdminUser {
    int id;
    String username;
    String password;

    @JsonProperty("create_time")
    private String createTime;

    public AdminUser(int id, String username, String password, String createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }

    public AdminUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
