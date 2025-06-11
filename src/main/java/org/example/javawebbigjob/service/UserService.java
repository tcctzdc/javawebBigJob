package org.example.javawebbigjob.service;


import org.example.javawebbigjob.entity.User;
import org.example.javawebbigjob.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> listAll() {
        return userMapper.findAll();
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public void add(User user) {
        // Set create_time to current time
        user.setCreateTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date()));
        userMapper.insert(user);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void delete(Long id) {
        userMapper.delete(id);
    }

    public void deleteBatch(List<Long> ids) {
        userMapper.deleteBatch(ids);
    }

    public List<User> findByPage(int offset, int size) {
        return userMapper.findByPage(offset, size);
    }

    public int countAll() {
        return userMapper.countAll();
    }

    public List<User> searchUsers(String username, String id) {
        if (username == null && id == null) {
            return userMapper.findAll();
        }
        return userMapper.search(username, id);
    }

}
