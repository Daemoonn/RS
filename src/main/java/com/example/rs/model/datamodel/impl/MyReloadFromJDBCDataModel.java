package com.example.rs.model.datamodel.impl;

import com.example.rs.model.datamodel.MyDataModel;
import com.example.rs.util.DBUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;

public class MyReloadFromJDBCDataModel implements MyDataModel {

    @Override
    public DataModel getMyDataModel() {
        MysqlDataSource dataSource = DBUtil.getMysqlDataSource();
        DataModel model = null;
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
        return model;
    }
}
