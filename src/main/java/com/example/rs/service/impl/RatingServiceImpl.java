package com.example.rs.service.impl;

import com.example.rs.dao.RatingDao;
import com.example.rs.domain.Rating;
import com.example.rs.model.recommender.MyRecommender;
import com.example.rs.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Resource(name = "MyUserBasedRecommender")
    private MyRecommender ucfRecommender;

    @Resource(name = "MyItemBasedRecommender")
    private MyRecommender icfRecommender;

    @Override
    public void addRating(Rating rating) {
        ratingDao.insertOne(rating);
        refresh();
    }

    @Override
    public void delRating(Rating rating) {
        ratingDao.deleteOne(rating.getUser_id(), rating.getMovie_id());
        refresh();
    }

    @Override
    public void updateRating(Rating rating) {
        ratingDao.updateOne(rating);
        refresh();
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

    void refresh() {
        ucfRecommender.refresh();
        icfRecommender.refresh();
    }
}
