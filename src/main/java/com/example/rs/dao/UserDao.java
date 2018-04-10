package com.example.rs.dao;

import com.example.rs.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    Integer insertOne(User user);

    Integer deleteOne(Long loginId);

    Integer updateOne(User user);

    User selectOne(Long loginId);
}
