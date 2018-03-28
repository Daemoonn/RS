package com.example.rs.model.recommender;

import com.example.rs.util.DBUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.AllSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.util.List;

public class MyItemBasedRecommender {
    private MysqlDataSource dataSource;
    private ReloadFromJDBCDataModel model;
    private ItemSimilarity similarity;
    private AllSimilarItemsCandidateItemsStrategy candidateStrategy;
    private ItemBasedRecommender recommender;

    public MyItemBasedRecommender() {
        dataSource = DBUtil.getMysqlDataSource();
        JDBCDataModel jdbcDataModel = new MySQLJDBCDataModel(dataSource,"taste_preferences","user_id",
                "item_id","preference","timestamp");
        /*
         * ReloadFromJDBCDataModel can accelerate the speed.
         * If use only JDBCDataModel can be slower and if the dataset is
         * large,it may exceed the max amount of the connections to MySql that your operating system limits.
         * Because it interacts with MySql too many times.You can try without ReloadFromJDBCDataModel and check the stacktrace in console.
         */
        try {
            model = new ReloadFromJDBCDataModel(jdbcDataModel);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        try {
            similarity = new PearsonCorrelationSimilarity(model); // computing the similarity
        } catch (TasteException e) {
            e.printStackTrace();
        }

        candidateStrategy = new AllSimilarItemsCandidateItemsStrategy(similarity);
        recommender = new GenericItemBasedRecommender(model, similarity, candidateStrategy, candidateStrategy);

    }

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

    public List<RecommendedItem> getRecommendedItemsMostSimilar(long itemId,int size) {
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
        System.out.println("getRecommendedItemsByUserId:");
        System.out.println(myItemBasedRecommender.getRecommendedItemsByUserId(1, 3)); //recommend 3 items for userID = 1
        System.out.println("getRecommendedItemsMostSimilar");
        System.out.println(myItemBasedRecommender.getRecommendedItemsMostSimilar(1, 3));  //recommend 3 items that are most similar to itemId = 1
    }
}
