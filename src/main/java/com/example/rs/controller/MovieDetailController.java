package com.example.rs.controller;

import com.example.rs.service.MovieDetailService;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MovieDetailController {
    @Autowired
    private MovieDetailService movieDetailService;

    @PostMapping("/searchMovieDetail")
    public ServerResponse searchMovieDetail(String movieId, String url_id, String en_title, String cn_title, String published_year) {
        Map<String,Object> map=new HashMap<>();
        if (movieId != null) {
            map.put("movieId", movieId);
        }
        if (url_id != null) {
            map.put("url_id", url_id);
        }
        if (en_title != null) {
            map.put("en_title", en_title);
        }
        if (cn_title != null) {
            map.put("cn_title", cn_title);
        }
        if (published_year != null) {
            map.put("published_year", published_year);
        }
        return ServerResponse.createSuccessResponse(movieDetailService.findConditions(map));
    }
}
