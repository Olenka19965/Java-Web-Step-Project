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
        String pathInfo = req.getPathInfo(); // /1
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing receiver ID");
            return;
        }

        int sender = ((User) req.getSession().getAttribute("user")).getId();
        int receiver = Integer.parseInt(pathInfo.substring(1));

        Map<String, Object> data = new HashMap<>();
        try {
            User ou = us.getProfileById(receiver);
            List<Message> ml = ms.readSome(sender, receiver, 0, 20);
            data.put("receiver", ou);
            data.put("messages", ml);
            te.render("chat.ftl", data, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
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

