package com.example.rs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import com.example.rs.config.WebSecurityConfig;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login/getLoginPage";
    }
}
