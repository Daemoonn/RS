package com.example.rs.controller;

import com.example.rs.domain.User;
import com.example.rs.service.UserService;
import com.example.rs.vo.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/getUser")
    public ServerResponse getUser(String loginId) {
        return ServerResponse.createSuccessResponse(userService.findByUserId(loginId));
    }

    @PostMapping("/addUser")
    public ServerResponse addUser(User user) {
        ServerResponse serverResponse = null;
        try {
            userService.addUser(user);
            serverResponse = serverResponse.createSuccessResponse(null);
        } catch (Exception e) {
            serverResponse = serverResponse.createErrorResponse(500,e.getMessage());
        }
        return serverResponse;
    }

    @PostMapping("/delUser")
    public ServerResponse delUser(String loginId) {
        ServerResponse serverResponse = null;
        try{
            userService.delUser(loginId);
            serverResponse = serverResponse.createSuccessResponse(null);
        }catch (Exception e) {
            serverResponse = serverResponse.createErrorResponse(500,e.getMessage());
        }
        return serverResponse;
    }

    @PostMapping("/updateUser")
    public ServerResponse updateUser(User user) {
        ServerResponse serverResponse = null;
        try {
            userService.updateUser(user);
            serverResponse = serverResponse.createSuccessResponse(null);
        } catch (Exception e) {
            serverResponse = serverResponse.createErrorResponse(500,e.getMessage());
        }
        return serverResponse;
    }
}
