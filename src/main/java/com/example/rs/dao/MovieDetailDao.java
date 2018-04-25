package com.example.rs.dao;

import com.example.rs.domain.MovieDetail;
import com.example.rs.vo.PageInfo;
import com.example.rs.vo.PageMovie;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MovieDetailDao {
    List<PageMovie> pageSelectIdwithName(@Param("index") int index, @Param("pageSize") int pageSize);
    List<PageMovie> pageSelectIdwithName(PageInfo pageInfo);
    List<PageMovie> selectIdwithName(@Param("movieId") Long movieId);
    List<PageMovie> selectForGenres(@Param("movieId") Long movieId);
    List<MovieDetail> selectConditions(Map<String,Object> map);
}