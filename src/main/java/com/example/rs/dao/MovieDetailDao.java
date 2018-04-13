package com.example.rs.dao;

import com.example.rs.domain.MovieDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MovieDetailDao {
    List<MovieDetail> pageSelectIdwithName(@Param("index") int index, @Param("pageSize") int pageSize);
    List<MovieDetail> selectConditions(Map<String,Object> map);
}