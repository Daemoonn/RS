package com.example.rs.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.example.rs.config.WebSecurityConfig.SESSION_KEY;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("From LoginInterceptor");
        System.out.println(request.getServletPath());
        HttpSession session = request.getSession();
        if (session.getAttribute(SESSION_KEY) != null)
            return true;

        // redirect
        String url = "/login/getLoginPage";
        response.sendRedirect(url);
        return false;

    }
}
