package com.example.rs.dao;

import com.example.rs.domain.Rating;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingDao {
    Integer insertOne(Rating rating);
    Integer deleteOne(Long userId, Long movieId);
    Integer updateOne(Rating rating);
    Float selectOne(Long userId, Long movieId);
    Integer isExist(Long userId, Long movieId);
}
