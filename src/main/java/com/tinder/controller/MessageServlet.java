package com.tinder.controller;

import com.tinder.domain.Message;
import com.tinder.exception.ServiceException;
import com.tinder.service.MessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class MessageServlet extends HttpServlet {
    private final TemplateEngine te;
    private final MessageService ms;

    public MessageServlet(TemplateEngine te, MessageService ms) {
        this.te = te;
        this.ms = ms;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String senderId = "123"; //GET_ID_FROM_COOKIES
        String receiverId = req.getParameter("id");

        Map<String, Object> data = new HashMap<>();

        System.out.println(data);

        List<Message> ml = null;
        try {
            ml = ms.readSome(senderId, receiverId, 0, 20);
            data.put("messages", ml);
            data.put("messages", Arrays.asList("Привіт", "Як справи?", "До зустрічі"));
            te.render("chat.ftl", data, resp);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
