package com.example.rs.service;

import com.example.rs.domain.Rating;
import org.springframework.stereotype.Service;

public interface RatingService {

    void addRating(Rating rating);

    void delRating(Rating rating);

    void updateRating(Rating rating);

    Float findRating(String userId, String movieId);

    boolean isExist(String userId, String movieId);
}
