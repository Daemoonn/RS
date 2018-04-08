package com.example.rs.service.impl;

import com.example.rs.domain.MovieDetail;
import com.example.rs.model.recommender.MyRecommender;
import com.example.rs.service.MovieDetailService;
import com.example.rs.service.RecommendService;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource(name = "MyUserBasedRecommender")
    private MyRecommender ucfRecommender;

    @Resource(name = "MyItemBasedRecommender")
    private MyRecommender icfRecommender;

    @Autowired
    private MovieDetailService movieDetailService;

    @Override
    public Map getRecommendations(long userID, int size) {
        Map<String, Object> m = new HashMap<>();
        List<MovieDetail> ucfDetails = new ArrayList<>();
        List<MovieDetail> icfDetails = new ArrayList<>();
        List<RecommendedItem> ucfRecommendations = ucfRecommender.getRecommendedItemsByUserId(userID, size);
        List<RecommendedItem> icfRecommendations = icfRecommender.getRecommendedItemsByUserId(userID, size);

        for (RecommendedItem r : ucfRecommendations) {
            System.out.println(new HashMap<String, Object>(){{ put("u_movieId", r.getItemID());}});
            List<MovieDetail> mdl = movieDetailService.findConditions(new HashMap<String, Object>(){{ put("movieId", r.getItemID());}});
            if (mdl.size() > 0) {
                ucfDetails.add(mdl.get(0));
            } else {
                System.out.println("empty mdl in ucf");
            }
        }
        for (RecommendedItem r : icfRecommendations) {
            System.out.println(new HashMap<String, Object>(){{ put("i_movieId", r.getItemID());}});
            List<MovieDetail> mdl = movieDetailService.findConditions(new HashMap<String, Object>(){{ put("movieId", r.getItemID());}});
            if (mdl.size() > 0) {
                icfDetails.add(mdl.get(0));
            } else {
                System.out.println("empty mdl in icf");
            }
        }
        m.put("ucf", ucfDetails);
        m.put("icf", icfDetails);
        return m;
    }

}
