package com.tinder.controller;

import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;
import com.tinder.service.LikeService;
import com.tinder.service.UserProfileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListServlet extends HttpServlet {
    private final TemplateEngine te;
    private final UserProfileService userProfileService;
    private final LikeService likeService;

    public UserListServlet(TemplateEngine te, UserProfileService userProfileService, LikeService likeService) {
        this.te = te;
        this.userProfileService = userProfileService;
        this.likeService = likeService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        try {
            int loggedInUserId = 1; // потрібно буде змінити на реальний юзер айді з кукі
            List<UserProfile> allProfiles = userProfileService.getAllProfiles();
            List<UserProfile> unvoted = new ArrayList<>();

            for (UserProfile profile : allProfiles) {
                if (profile.getId() == loggedInUserId) continue;
                Boolean status = likeService.getLikeStatus(loggedInUserId, profile.getId());
                if (status == null) {
                    unvoted.add(profile); // тут показуємо тільки тих кого ще не лайкнули і не показуємо себе
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

        if (userIdStr != null && action != null) {
            try {
                int targetUserId = Integer.parseInt(userIdStr);
                int loggedInUserId = 1; // потрібно буде змінити на реальний юзер айді з кукі
                boolean liked = "yes".equalsIgnoreCase(action);
                likeService.setLikeStatus(loggedInUserId, targetUserId, liked);
            } catch (NumberFormatException ignored) {}
        }
        resp.sendRedirect("/userlist");
    }
}
