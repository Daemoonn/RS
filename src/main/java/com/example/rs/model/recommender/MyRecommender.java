package com.example.rs.model.recommender;

import com.example.rs.domain.Rating;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MyRecommender {
    List<RecommendedItem> getRecommendedItemsByUserId(long userID, int size);
    void refresh();
}
