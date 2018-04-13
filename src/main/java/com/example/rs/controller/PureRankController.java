package com.example.rs.controller;

import com.example.rs.dao.MovieDetailDao;
import com.example.rs.service.MovieDetailService;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PureRankController {
    @Autowired
    private MovieDetailService movieDetailService;

    @PostMapping("/pure_rank")
    public ServerResponse getPureRankPage(String pageNum, String pageSize) {
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize:" + pageSize);
        return ServerResponse.createSuccessResponse(
                movieDetailService.pageSelectIdwithName(Integer.parseInt(pageNum), Integer.parseInt(pageSize)));
    }
}
