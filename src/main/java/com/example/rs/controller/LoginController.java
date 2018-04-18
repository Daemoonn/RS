package com.example.rs.controller;

import com.example.rs.config.WebSecurityConfig;
import com.example.rs.domain.User;
import com.example.rs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/getLoginPage")
    public ModelAndView getLoginPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("login");
        User user = new User();
        model.addAttribute("user", user);
        return modelAndView;
    }
    @PostMapping("/login/loginCheck")
    public String loginCheck(@ModelAttribute User user, HttpSession session) {
        String loginId = user.getLoginId();
        String pwd = user.getPassword();
        User userf = userService.findByUserId(loginId);
        if (userf != null && userf.getPassword().equals(pwd)) {
            // session never timeout
            session.setMaxInactiveInterval(0);
            session.setAttribute(WebSecurityConfig.SESSION_KEY, loginId);
            return "redirect:/home";
        } else {
            return "redirect:/login/getLoginPage";
        }
    }
}
