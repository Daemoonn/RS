package com.example.rs.controller;

import com.example.rs.service.MovieDetailService;
import com.example.rs.vo.PageInfo;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
    @Autowired
    private MovieDetailService movieDetailService;

    @PostMapping("/search1")
    public ModelAndView searchMovie(@RequestParam("key_word") String keyWord, Model model) {
        System.out.println("keyWord: " + keyWord);
        model.addAttribute("keyWord", keyWord);
        return new ModelAndView("search_page");
    }

    @ResponseBody
    @PostMapping("/search2")
    public ServerResponse search(String pageNum, String pageSize, String keyWord) {
        System.out.println("spageNum:" + pageNum + " spageSize:" + pageSize + " keyWord:" + keyWord);
        return ServerResponse.createSuccessResponse(movieDetailService.searchWithName(
                new PageInfo(Integer.parseInt(pageNum), Integer.parseInt(pageSize), keyWord)));
    }

//    @PostMapping("/rank")
    public ServerResponse getRankPage(String pageNum, String pageSize, String typeChooser, String radioChooser) {
        System.out.println("pageNum:" + pageNum + " pageSize:" + pageSize + " typeChooser:"
                + typeChooser + " radioChooser:" + radioChooser);
        return ServerResponse.createSuccessResponse(
                movieDetailService.pageSelectIdwithName(new PageInfo(Integer.parseInt(pageNum), Integer.parseInt(pageSize), typeChooser, radioChooser)));
    }
}
