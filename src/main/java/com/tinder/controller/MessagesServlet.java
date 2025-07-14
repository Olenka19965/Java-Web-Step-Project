package com.tinder.controller;

import com.tinder.model.Message;
import com.tinder.model.User;
import com.tinder.service.message.MessageService;
import com.tinder.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class MessagesServlet extends HttpServlet {
    private final TemplateEngine te;
    private final MessageService ms;
    private final UserService us;

    public MessagesServlet(TemplateEngine te, MessageService ms, UserService us) {
        this.te = te;
        this.ms = ms;
        this.us = us;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");

        if (currentUser == null) {
            resp.sendRedirect("/login");
            return;
        }

        String pathInfo = req.getPathInfo(); // /1

        if (pathInfo == null || pathInfo.equals("/")) {
            // 👉 редірект на першого співрозмовника
            try {
                // отримаємо список останніх повідомлень (напр., унікальні receiverId)
                List<Message> messages = ms.readSome(currentUser.getId(), -1, 0, 1); // або спеціальний метод "getLastConversations()"

                if (!messages.isEmpty()) {
                    int firstReceiverId = messages.get(0).receiverId();
                    resp.sendRedirect("/messages/" + firstReceiverId);
                } else {
                    // немає листувань — перенаправляємо на /users або показуємо заглушку
                    resp.sendRedirect("/liked");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        try {
            int receiverId = Integer.parseInt(pathInfo.substring(1));
            User receiver = us.getProfileById(receiverId);
            List<Message> messageList = ms.readSome(currentUser.getId(), receiverId, 0, 20);

            Map<String, Object> data = new HashMap<>();
            data.put("receiver", receiver);
            data.put("messages", messageList);
            te.render("chat.ftl", data, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid user ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // /1
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing receiver ID");
            return;
        }

        try {
            int sId = ((User) req.getSession().getAttribute("user")).getId();
            int rId = Integer.parseInt(pathInfo.substring(1));
            String content = req.getParameter("message");
            Timestamp t = new Timestamp(new Date().getTime());

            ms.create(new Message(null, sId, null, null, rId, null, null, content, t));
            resp.sendRedirect(String.format("/messages/%d", rId)); // оновлений redirect
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}

