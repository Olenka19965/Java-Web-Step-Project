package com.tinder.controller;

import com.tinder.exception.DaoException;
import com.tinder.model.User;
import com.tinder.service.like.LikeService;
import com.tinder.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListServlet extends HttpServlet {
    private final TemplateEngine te;
    private final UserService userProfileService;
    private final LikeService likeService;

    public UserListServlet(TemplateEngine te, UserService userProfileService, LikeService likeService) {
        this.te = te;
        this.userProfileService = userProfileService;
        this.likeService = likeService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        try {
            User currentUser = (User) req.getSession().getAttribute("user");
            if (currentUser == null) {
                resp.sendRedirect("/login"); // або інша сторінка логіну
                return;
            }
            data.put("user", currentUser);
            int loggedInUserId = currentUser.getId(); // замінити на справжній айді з кукі
            List<User> allProfiles = userProfileService.getAllProfiles();
            List<User> unvoted = new ArrayList<>();

            for (User profile : allProfiles) {
                if (profile.getId() == loggedInUserId) continue;
                Boolean status = likeService.getLikeStatus(loggedInUserId, profile.getId());
                if (status == null) {
                    unvoted.add(profile);
                }
            }
            data.put("users", unvoted);
        } catch (DaoException e) {
            e.printStackTrace();
            data.put("users", List.of());
        }
        te.render("people-list.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userIdStr = req.getParameter("userId");
        String action = req.getParameter("action");

        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/login");
            return;
        }

        if (userIdStr != null && action != null) {
            try {
                int targetUserId = Integer.parseInt(userIdStr);
                int loggedInUserId = currentUser.getId();
                boolean liked = "yes".equalsIgnoreCase(action);
                likeService.setLikeStatus(loggedInUserId, targetUserId, liked);
            } catch (NumberFormatException ignored) {
            }
        }

        resp.sendRedirect("/users");
    }

}