package com.example.rs.service;

import java.util.Map;

public interface RecommendService {
    Map<String, Object> getRecommendations(long userID, int size);
}
