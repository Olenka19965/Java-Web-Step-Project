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
        HttpSession session = req.getSession();

        try {
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                resp.sendRedirect("/login");
                return;
            }

            List<User> allProfiles = userProfileService.getAllProfiles();
            List<User> availableProfiles = new ArrayList<>();

            // Видаляємо себе зі списку
            for (User u : allProfiles) {
                if (u.getId() != currentUser.getId()) {
                    availableProfiles.add(u);
                }
            }

            // Якщо немає інших користувачів
            if (availableProfiles.isEmpty()) {
                data.put("user", null);
                te.render("people-list.ftl", data, resp);
                return;
            }

            // Отримуємо поточний індекс з сесії
            Integer index = (Integer) session.getAttribute("userIndex");
            if (index == null) {
                index = 0;
            }

            // Гарантуємо, що індекс не вийде за межі
            if (index >= availableProfiles.size()) {
                index = 0;
            }

            User nextUser = availableProfiles.get(index);
            data.put("user", nextUser);
            session.setAttribute("userIndex", index); // Зберігаємо індекс, щоб POST знав кого було показано

        } catch (DaoException e) {
            e.printStackTrace();
            data.put("user", null);
        }

        te.render("people-list.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/login");
            return;
        }

        String userIdStr = req.getParameter("userId");
        String action = req.getParameter("action");

        if (userIdStr != null && action != null) {
            try {
                int targetUserId = Integer.parseInt(userIdStr);
                boolean liked = "yes".equalsIgnoreCase(action);
                likeService.setLikeStatus(currentUser.getId(), targetUserId, liked);
            } catch (NumberFormatException ignored) {}
        }

        // Збільшуємо індекс перегляду
        Integer index = (Integer) session.getAttribute("userIndex");
        if (index == null) {
            index = 0;
        }
        session.setAttribute("userIndex", index + 1);

        // Перенаправляємо назад
        resp.sendRedirect("/users");
    }
}