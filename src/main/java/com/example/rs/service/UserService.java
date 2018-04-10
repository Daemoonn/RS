package com.example.rs.service;

import com.example.rs.domain.User;

public interface UserService {

    void addUser(User user) throws Exception;

    void delUser(String userId) throws Exception;

    void updateUser(User user);

    User findByUserId(String userId);

}
