package com.tinder.controller;

import com.tinder.model.Auth;
import com.tinder.model.User;
import com.tinder.service.auth.AuthService;
import com.tinder.service.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginFilter implements Filter {
    private final TemplateEngine templateEngine;
    private final AuthService authService;
    private final UserService userService;

    public LoginFilter(TemplateEngine te, AuthService authService, UserService userService) {
        this.templateEngine = te;
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getServletPath();

        if (path.equals("/login") || path.startsWith("/assets")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}

