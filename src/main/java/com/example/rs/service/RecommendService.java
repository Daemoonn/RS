package com.example.rs.service;

import java.util.Map;

public interface RecommendService {
    Map<String, Object> getRecommendations1(long userId, long movieId, String genres, int size);
    Map<String, Object> getRecommendations2(long userId, int size);
}
