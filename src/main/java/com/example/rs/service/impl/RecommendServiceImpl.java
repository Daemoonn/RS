package com.example.rs.service.impl;

import com.example.rs.domain.MovieDetail;
import com.example.rs.model.recommender.MyRecommender;
import com.example.rs.model.recommender.impl.MyItemBasedRecommender;
import com.example.rs.service.MovieDetailService;
import com.example.rs.service.RecommendService;
import com.example.rs.vo.PageMovie;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource(name = "MyUserBasedRecommender")
    private MyRecommender ucfRecommender;

    @Resource(name = "MyItemBasedRecommender")
    private MyItemBasedRecommender icfRecommender;

    @Autowired
    private MovieDetailService movieDetailService;

    private final static int candidateNum = 50;

    @Override
    public Map<String, Object> getRecommendations1(long userId, long movieId, String genres, int size) {
        Map<String, Object> m = new HashMap<>();
        List<PageMovie> ucfDetails = new ArrayList<>();
        List<PageMovie> icfDetails = new ArrayList<>();
        List<PageMovie> mostSimilarDetails = new ArrayList<>();
        List<RecommendedItem> ucfRecommendations = ucfRecommender.getRecommendedItemsByUserId(userId, candidateNum);
        List<RecommendedItem> icfRecommendations = icfRecommender.getRecommendedItemsByUserId(userId, candidateNum);
        List<RecommendedItem> mostSimilarItems = icfRecommender.getItemsMostSimilar(movieId, candidateNum);
        Map<Integer, Integer> ucfm = new HashMap<>();
        Map<Integer, Integer> icfm = new HashMap<>();
        Map<Integer, Integer> mostm = new HashMap<>();
        String[] genresList = genres.split(",");
        for (int i = 0; i < genresList.length; i++) {
            genresList[i] = genresList[i].trim();
            System.out.print(genresList[i] + ",");
        }
        System.out.println();

        if (ucfRecommendations != null) {
            ucfDetails = getSortedDetails(ucfRecommendations, ucfDetails, genresList, ucfm, size);
        }
        m.put("ucf", ucfDetails);

        if (icfRecommendations != null) {
            icfDetails = getSortedDetails(icfRecommendations, icfDetails, genresList, icfm, size);
        }
        m.put("icf", icfDetails);

        if (mostSimilarItems != null) {
            mostSimilarDetails = getSortedDetails(mostSimilarItems, mostSimilarDetails, genresList, mostm, size);
        }
        m.put("mostSimilarItems", mostSimilarDetails);

        return m;
    }

    @Deprecated
    @Override
    public Map getRecommendations2(long userId, int size) {
        Map<String, Object> m = new HashMap<>();
        List<MovieDetail> ucfDetails = new ArrayList<>();
        List<MovieDetail> icfDetails = new ArrayList<>();
        List<RecommendedItem> ucfRecommendations = ucfRecommender.getRecommendedItemsByUserId(userId, size);
        List<RecommendedItem> icfRecommendations = icfRecommender.getRecommendedItemsByUserId(userId, size);

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

    private Map<Integer, Integer> countGenresSimilarity(List<RecommendedItem> recommendedItems, String[] genresList, Map<Integer, Integer> cm) {
//        long sumt = 0;
        for (int i = 0; i < recommendedItems.size(); i++) {
            RecommendedItem r = recommendedItems.get(i);
            String tg;
//            long startTime = System.currentTimeMillis();
            List<PageMovie> mdl = movieDetailService.selectForGenres(r.getItemID());
//            long endTime = System.currentTimeMillis();
//            sumt += (endTime - startTime);
//            System.out.println("selectForGenres 程序运行时间：" + (endTime - startTime) + "ms");
            if (mdl.size() > 0) {
                tg = mdl.get(0).getGenres();
                String[] tgList = tg.split(",");
                for (int j = 0; j < tgList.length; j++) {
                    tgList[j] = tgList[j].trim();
                }
                int cou = 0;
                for (int j = 0; j < genresList.length; j++) {
                    for (int k = 0; k < tgList.length; k++) {
                        if (genresList[j].equals(tgList[k])) {
                            cou = cou + 1;
                        }
                    }
                }
                cm.put(i, cou);
            } else {
                System.out.println("empty mdl in ucf");
            }
        }
//        System.out.println("sumt 程序运行时间：" + sumt + "ms");
        return cm;
    }

    private List<Map.Entry<Integer,Integer>> sortGenres(Map<Integer, Integer> m) {
        List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer,Integer>>(m.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer,Integer>>() {
            //DESC
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list;
    }

    private List<PageMovie> getSortedDetails(List<RecommendedItem> recommendations, List<PageMovie> details, String[] genresList, Map<Integer, Integer> m, int size) {
        List<Map.Entry<Integer,Integer>> list;
        if (recommendations != null) {
            m.clear();

//            long startTime = System.currentTimeMillis();
            m = countGenresSimilarity(recommendations, genresList, m);
//            long endTime = System.currentTimeMillis();
//            System.out.println("countGenresSimilarity 程序运行时间：" + (endTime - startTime) + "ms");


//            startTime = System.currentTimeMillis();
            list = sortGenres(m);
//            endTime = System.currentTimeMillis();
//            System.out.println("sortGenres 程序运行时间：" + (endTime - startTime) + "ms");



//            startTime = System.currentTimeMillis();
            for (int i = 0; i < list.size() && i < size; i++) {
                Map.Entry<Integer, Integer> mapping = list.get(i);
                List<PageMovie> mdl = movieDetailService.selectForGenres(recommendations.get(mapping.getKey()).getItemID());
                if (mdl.size() > 0) {
                    details.add(mdl.get(0));
                } else {
                    System.out.println("empty mdl in ucf");
                }
            }
//            endTime = System.currentTimeMillis();
//            System.out.println("details.add 程序运行时间：" + (endTime - startTime) + "ms");

        }
        return details;
    }
}
