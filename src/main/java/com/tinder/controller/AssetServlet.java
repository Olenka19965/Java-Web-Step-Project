package com.tinder.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

public class AssetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo(); // напр. /style.css
        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        InputStream resource = getClass().getResourceAsStream("/assets" + path);
        if (resource == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = req.getServletContext().getMimeType(path);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);
        resource.transferTo(resp.getOutputStream());
    }
}

