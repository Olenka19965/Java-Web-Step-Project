package com.tinder.controller;

import com.tinder.dao.user.UserProfileDao;
import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserListServlet extends HttpServlet {
    private final TemplateEngine te;
    private final UserProfileDao userProfileDao;
    private final List<UserProfile> likedUsers = new ArrayList<>();

    public UserListServlet(TemplateEngine te, UserProfileDao userProfileDao) {
        this.te = te;
        this.userProfileDao = userProfileDao;
//        this.userProfileService = userProfileService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        try {
            List<UserProfile> users = userProfileDao.findAll();
            data.put("users", users);
        } catch (DaoException e) {
            e.printStackTrace();
            data.put("users", List.of());
        }
        data.put("likedUsers", likedUsers);
        te.render("people-list.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userIdStr = req.getParameter("userId");
        String action = req.getParameter("action");

        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String param = paramNames.nextElement();
            System.out.println(param + " = " + req.getParameter(param));
        }
        if (userIdStr != null && action != null) {
            try {
                int userId = Integer.parseInt(userIdStr);
                try {
                    UserProfile user = userProfileDao.findById(userId);
//                    UserProfile user = usersService.find(userId);
                    if (user != null) {
                        if ("yes".equalsIgnoreCase(action)) {
                            if (!likedUsers.contains(user)) {
                                likedUsers.add(user);
                            }
                        }
                    }
                } catch (DaoException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException ignored) {
            }
        }
        resp.sendRedirect("/userlist");
    }
}
