package com.example.rs.controller;

import com.example.rs.domain.MovieDetail;
import com.example.rs.service.MovieDetailService;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieDetailController {
    @Autowired
    private MovieDetailService movieDetailService;

    @GetMapping("/searchMovieDetail")
    public ModelAndView searchMovieDetail(Model model, String movieId, String url_id, String en_title, String cn_title, String published_year, String avg2) {
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
        }
//        return ServerResponse.createSuccessResponse(movieDetailService.findConditions(map));
        return modelAndView;
    }
}
