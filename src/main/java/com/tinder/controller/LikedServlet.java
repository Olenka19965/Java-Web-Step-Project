package com.tinder.controller;

import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;
import com.tinder.service.LikeService;
import com.tinder.service.UserProfileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikedServlet extends HttpServlet {
    private final TemplateEngine te;
    private final UserProfileService userProfileService;
    private final LikeService likeService;

    public LikedServlet(TemplateEngine te, UserProfileService userProfileService, LikeService likeService) {
        this.te = te;
        this.userProfileService = userProfileService;
        this.likeService = likeService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = getLoggedInUserId(req);
        Map<String, Object> data = new HashMap<>();

        try {
            List<UserProfile> likedUsers = likeService.getLikedProfiles(userId);
            data.put("likedUsers", likedUsers);
        } catch (DaoException e) {
            e.printStackTrace();
            data.put("likedUsers", List.of());
        }
        te.render("like-page.ftl", data, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = getLoggedInUserId(req);
        String action = req.getParameter("action");
        String targetIdStr = req.getParameter("targetId");

        if (targetIdStr != null) {
            try {
                int targetId = Integer.parseInt(targetIdStr);

                switch (action.toLowerCase()) {
                    case "like":
                        likeService.setLikeStatus(userId, targetId, true);
                        break;
                    case "dislike":
                        likeService.setLikeStatus(userId, targetId, false);
                        break;
                    case "remove":
                        likeService.setLikeStatus(userId, targetId, null);
                        break;
                    default:
                        System.out.println("Невідома дія: " + action);
                }

            } catch (NumberFormatException | DaoException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("/liked");
    }
    private int getLoggedInUserId(HttpServletRequest req) {
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if ("userId".equals(cookie.getName())) {
                    try {
                        return Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return 1;
    }
}
