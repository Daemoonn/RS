package com.example.rs.service.impl;

import com.example.rs.dao.UserDao;
import com.example.rs.domain.User;
import com.example.rs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void addUser(User user) throws Exception {
        Integer rows = userDao.insertOne(user);
        if(rows == 0) {
            throw new Exception("failed to add one user");
        }
    }

    @Override
    public void delUser(String userId) throws Exception {
        Integer rows = userDao.deleteOne(Long.parseLong(userId));
        if(rows == 0) {
            throw new Exception("failed to delete one user");
        }
    }

    @Override
    public void updateUser(User user) {
        userDao.updateOne(user);
    }

    @Override
    public User findByUserId(String userId) {
        return userDao.selectOne(Long.parseLong(userId));
    }
}
