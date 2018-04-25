package com.example.rs.controller;

import com.example.rs.service.RecommendService;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendController {
    @Autowired
    private RecommendService recommendService;

    @PostMapping("/recommend")
    public ServerResponse recommend(String userId, String genres, String size) {
        return ServerResponse.createSuccessResponse(recommendService.getRecommendations1(Long.parseLong(userId), genres, Integer.parseInt(size)));
    }
}
