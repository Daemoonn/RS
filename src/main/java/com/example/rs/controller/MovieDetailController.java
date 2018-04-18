package com.example.rs.controller;

import com.example.rs.domain.MovieDetail;
import com.example.rs.domain.Rating;
import com.example.rs.service.MovieDetailService;
import com.example.rs.service.RatingService;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.rs.config.WebSecurityConfig;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieDetailController {
    @Autowired
    private MovieDetailService movieDetailService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/searchMovieDetail")
    public ModelAndView searchMovieDetail(HttpSession session, Model model, String movieId, String url_id, String en_title, String cn_title, String published_year, String avg2) {
        ModelAndView modelAndView = new ModelAndView("movie_detail");
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
        List<MovieDetail> mdl = movieDetailService.findConditions(map);
        if (!mdl.isEmpty()) {
            System.out.println("put " + mdl.get(0).getMovieId() + " " + mdl.get(0).getEn_title() + " movieDetail into model");
            model.addAttribute("movieDetail", mdl.get(0));
            model.addAttribute("avg2", avg2);
            String userId = (String)session.getAttribute(WebSecurityConfig.SESSION_KEY);
            Float rating = ratingService.findRating(userId, movieId);
            model.addAttribute("user_star", rating);
        }
//        return ServerResponse.createSuccessResponse(movieDetailService.findConditions(map));
        return modelAndView;
    }

    @Deprecated
    @PostMapping("/insertRating")
    public void insertRating(HttpSession session, String movieId, String rating) {
        System.out.println("insertRating");
        if (rating == null || rating.equals("")) {
            System.out.println("no rating");
        } else {
            String userId = (String)session.getAttribute(WebSecurityConfig.SESSION_KEY);
            System.out.println("userId:" + userId + " movieId:" + movieId + " rating:" + rating);
            Rating rating1 = new Rating(userId, movieId, rating);
            ratingService.addRating(rating1);
        }
    }

    @Deprecated
    @PostMapping("/updateRating")
    public void updateRating(HttpSession session, String movieId, String rating) {
        System.out.println("updateRating");
        if (rating == null || rating.equals("")) {
            System.out.println("no rating");
        } else {
            String userId = (String)session.getAttribute(WebSecurityConfig.SESSION_KEY);
            System.out.println("userId:" + userId + " movieId:" + movieId + " rating:" + rating);
            Rating rating1 = new Rating(userId, movieId, rating);
            ratingService.updateRating(rating1);
        }
    }

    @Deprecated
    @PostMapping("/deleteRating")
    public void deleteRating(HttpSession session, String movieId, String rating) {
        System.out.println("deleteRating");
        if (rating == null || rating.equals("")) {
            System.out.println("no rating");
            String userId = (String)session.getAttribute(WebSecurityConfig.SESSION_KEY);
            System.out.println("userId:" + userId + " movieId:" + movieId + " rating:" + rating);
            ratingService.delRating(userId, movieId);
        }
    }

    @PostMapping("/crudRating")
    public void crudRating(HttpSession session, String movieId, String rating) {
        String userId = (String)session.getAttribute(WebSecurityConfig.SESSION_KEY);
        System.out.println("userId:" + userId + " movieId:" + movieId);
        if (rating == null || rating.equals("")) {
            ratingService.delRating(userId, movieId);
            System.out.println("clear rating");
        } else {
            Rating rating1 = new Rating(userId, movieId, rating);
            if (ratingService.isExist(userId, movieId)) {
                ratingService.updateRating(rating1);
            } else {
                ratingService.addRating(rating1);
            }
        }
    }

    @PostMapping("/searchRating")
    public ServerResponse searchRating(HttpSession session, String movieId) {
        System.out.println("searchRating");
        String userId = (String)session.getAttribute(WebSecurityConfig.SESSION_KEY);
        System.out.println("userId:" + userId + " movieId:" + movieId);
        return ServerResponse.createSuccessResponse(ratingService.findRating(userId, movieId));
    }
}
