package com.example.rs.model.recommender.impl;
import com.example.rs.model.datamodel.impl.MyReloadFromJDBCDataModel;
import com.example.rs.model.recommender.MyRecommender;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("MyItemBasedRecommender")
public class MyItemBasedRecommender implements MyRecommender {
    private DataModel model;
    private ItemSimilarity similarity;
    private ItemBasedRecommender recommender;

    public MyItemBasedRecommender() {
        model = new MyReloadFromJDBCDataModel().getMyDataModel();
        try {
            similarity = new PearsonCorrelationSimilarity(model); // computing the similarity
        } catch (TasteException e) {
            e.printStackTrace();
        }
        recommender = new GenericItemBasedRecommender(model, similarity);
    }

    @Override
    public List<RecommendedItem> getRecommendedItemsByUserId(long userID,int size){
        List<RecommendedItem> recommendations = null;
        try {
            recommendations = recommender.recommend(userID, size); // get recommend results
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return recommendations;
    }

    public List<RecommendedItem> getItemsMostSimilar(long itemId,int size) {
        List<RecommendedItem> recommendations = null;
        try {
            recommendations = recommender.mostSimilarItems(itemId, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  recommendations;
    }

    public static void main(String[] args) {
        MyItemBasedRecommender myItemBasedRecommender = new MyItemBasedRecommender();
        System.out.println(myItemBasedRecommender.getRecommendedItemsByUserId(1, 3));
    }

//    public static void main(String[] args) {
//        //evaluate MyItemBasedRecommender
//        RandomUtils.useTestSeed();
//        DataModel model = new MyReloadFromJDBCDataModel().getMyDataModel();
//        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
//        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
//            @Override
//            public Recommender buildRecommender(DataModel model) throws TasteException {
//                ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
//                return new GenericItemBasedRecommender(model, similarity);
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
