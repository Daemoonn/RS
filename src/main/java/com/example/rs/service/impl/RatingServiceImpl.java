package com.example.rs.service.impl;

import com.example.rs.dao.RatingDao;
import com.example.rs.domain.Rating;
import com.example.rs.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Override
    public void addRating(Rating rating) {
        ratingDao.insertOne(rating);
    }

    @Override
    public void delRating(String userId, String movieId) {
        ratingDao.deleteOne(Long.parseLong(userId), Long.parseLong(movieId));
    }

    @Override
    public void updateRating(Rating rating) {
        ratingDao.updateOne(rating);
    }

    @Override
    public Float findRating(String userId, String movieId) {
        return ratingDao.selectOne(Long.parseLong(userId), Long.parseLong(movieId));
    }

    @Override
    public boolean isExist(String userId, String movieId) {
        int op = ratingDao.isExist(Long.parseLong(userId), Long.parseLong(movieId));
        return op == 1 ? true : false;
    }
}
