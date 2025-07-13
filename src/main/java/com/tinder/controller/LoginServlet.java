package com.tinder.controller;

import com.tinder.model.Auth;
import com.tinder.model.User;
import com.tinder.service.auth.AuthService;
import com.tinder.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.LoginService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    TemplateEngine templateEngine;
    AuthService authService;
    UserService userService;

    public LoginServlet(TemplateEngine te, AuthService as, UserService us) {
        this.templateEngine = te;
        this.authService = as;
        this.userService = us;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>(1);

        //show login page
        templateEngine.render("login.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("email"); // або "login" — залежно від input
        String password = req.getParameter("password");

        authService.login(login, password).ifPresentOrElse(
                auth -> {
                    try {
                        User user = userService.getProfileById(auth.getUserId());

                        req.getSession().setAttribute("user", user);

                        resp.sendRedirect("/users");
                    } catch (Exception e) {
                        throw new RuntimeException("Помилка при завантаженні профілю", e);
                    }
                },
                () -> {
                    try {
                        resp.sendRedirect("/login?error=true");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}