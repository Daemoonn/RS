package com.example.rs.model.recommender.impl;
import com.example.rs.domain.Rating;
import com.example.rs.model.datamodel.impl.MyReloadFromJDBCDataModel;
import com.example.rs.model.recommender.MyRecommender;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("MyUserBasedRecommender")
public class MyUserBasedRecommender implements MyRecommender{
    private DataModel model;
    private UserSimilarity similarity;
    private UserNeighborhood neighborhood;
    private UserBasedRecommender recommender;

    public MyUserBasedRecommender() {
        System.out.println("new MyUserBasedRecommender");
        model = new MyReloadFromJDBCDataModel().getMyDataModel();
        try {
            similarity = new PearsonCorrelationSimilarity(model);
            neighborhood = new NearestNUserNeighborhood(6, similarity, model);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
    }

    @Override
    public List getRecommendedItemsByUserId(long userID, int size) {
        List<RecommendedItem> recommendations = null;
        try {
            recommendations = recommender.recommend(userID, size);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return recommendations;
    }

    @Override
    public void refresh() {
        recommender.refresh(null);
    }

    public long[] getUsersMostSimilar(long userId,int size) {
        long[] mostSimilarUsers = null;
        try {
            mostSimilarUsers = recommender.mostSimilarUserIDs(userId, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mostSimilarUsers;
    }

    public static void main(String[] args) {
        MyUserBasedRecommender myUserBasedRecommender = new MyUserBasedRecommender();
//        myUserBasedRecommender.setPreference(1003, 135436, (float) 5.0);
        System.out.println(myUserBasedRecommender.getRecommendedItemsByUserId(1003, 100));
    }

//    public static void main(String[] args) {
//        //evaluate MyUserBasedRecommender
//        RandomUtils.useTestSeed();
//        DataModel model = new MyReloadFromJDBCDataModel().getMyDataModel();
//        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
//        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
//            @Override
//            public Recommender buildRecommender(DataModel model) throws TasteException {
//                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//                UserNeighborhood neighborhood = new NearestNUserNeighborhood(6, similarity, model);
//                return new GenericUserBasedRecommender(model, neighborhood, similarity);
//            }
//        };
//        IRStatistics stats = null;
//        try {
//            stats = evaluator.evaluate(recommenderBuilder, null, model, null, 10, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
//        } catch (TasteException e) {
//            e.printStackTrace();
//        }
//        System.out.println(stats.getPrecision());
//        System.out.println(stats.getRecall());
//        System.out.println(stats.getF1Measure());
//
//        RecommenderEvaluator recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
//        double score = -1.0;
//        try {
//            score = recommenderEvaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
//        } catch (TasteException e) {
//            e.printStackTrace();
//        }
//        System.out.println(score);
//    }
}
