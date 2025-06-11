package org.example.javawebbigjob.service;


import org.example.javawebbigjob.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;

    public String getPasswordByUsername(String username){
        return adminUserMapper.getPasswordByUsername(username);
    };
}
