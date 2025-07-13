package com.tinder.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AssetServlet extends HttpServlet {
    private final String basePath;

    public AssetServlet(String basePath) {
        this.basePath = basePath;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        File file = new File(basePath, path);
        if (!file.exists() || file.isDirectory()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Визначення MIME типу
        String mimeType = req.getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);
        resp.setContentLengthLong(file.length());
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            in.transferTo(out);
        }
    }
}

