package com.example.rs.controller;

import com.example.rs.dao.MovieDetailDao;
import com.example.rs.service.MovieDetailService;
import com.example.rs.vo.PageInfo;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankController {
    @Autowired
    private MovieDetailService movieDetailService;

    @PostMapping("/rank")
    public ServerResponse getRankPage(String pageNum, String pageSize, String typeChooser, String radioChooser) {
        System.out.println("pageNum:" + pageNum + " pageSize:" + pageSize + " typeChooser:"
                + typeChooser + " radioChooser:" + radioChooser);
        return ServerResponse.createSuccessResponse(
                movieDetailService.pageSelectIdwithName(new PageInfo(Integer.parseInt(pageNum), Integer.parseInt(pageSize), typeChooser, radioChooser)));
    }
}
