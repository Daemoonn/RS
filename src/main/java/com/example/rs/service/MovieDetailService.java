package com.example.rs.service;

import com.example.rs.domain.MovieDetail;
import com.example.rs.vo.PageInfo;
import com.example.rs.vo.PageMovie;

import java.util.List;
import java.util.Map;

public interface MovieDetailService {
    List<PageMovie> pageSelectIdwithName(int pageNum, int pageSize);
    List<PageMovie> pageSelectIdwithName(PageInfo pageInfo);
    List<PageMovie> selectIdwithName(Long movieId);
    List<PageMovie> selectForGenres(Long movieId);
    List<MovieDetail> findConditions(Map<String,Object> map);
}
