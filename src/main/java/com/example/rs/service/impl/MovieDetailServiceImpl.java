package com.example.rs.service.impl;

import com.example.rs.dao.MovieDetailDao;
import com.example.rs.domain.MovieDetail;
import com.example.rs.util.CalcIndex;
import com.example.rs.vo.PageInfo;
import com.example.rs.vo.PageMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rs.service.MovieDetailService;

import java.util.List;
import java.util.Map;

@Service
public class MovieDetailServiceImpl implements MovieDetailService {

    @Autowired
    private MovieDetailDao movieDetailDao;

    @Override
    public List<PageMovie> pageSelectIdwithName(int pageNum, int pageSize) {
        return movieDetailDao.pageSelectIdwithName(CalcIndex.parseIndex(pageNum, pageSize), pageSize);
    }

    @Override
    public List<PageMovie> pageSelectIdwithName(PageInfo pageInfo) {
        return movieDetailDao.pageSelectIdwithName(pageInfo);
    }

    @Override
    public List<PageMovie> selectIdwithName(Long movieId) {
        return movieDetailDao.selectIdwithName(movieId);
    }

    @Override
    public List<PageMovie> selectForGenres(Long movieId) {
        return movieDetailDao.selectForGenres(movieId);
    }

    @Override
    public List<MovieDetail> findConditions(Map<String, Object> map) {
        return movieDetailDao.selectConditions(map);
    }

}
