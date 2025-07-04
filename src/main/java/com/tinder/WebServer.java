package com.tinder;

import com.tinder.controller.HelloServlet;
import com.tinder.controller.MessageServlet;
import com.tinder.controller.TemplateEngine;
import com.tinder.controller.UserListServlet;
import com.tinder.dao.message.MessageDao;
import com.tinder.dao.message.MessageSqlDao;
import com.tinder.dao.model.SqlUserProfileDao;
import com.tinder.service.MessageService;
import com.tinder.utils.DbUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.Connection;
import java.sql.SQLException;

public class WebServer {
    public static void main(String[] args) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            System.out.println("Підключення успішне!");

            Server server = new Server(8080);

            TemplateEngine te = new TemplateEngine();
            SqlUserProfileDao userDao = new SqlUserProfileDao();
            ServletContextHandler handler = new ServletContextHandler();

            handler.addServlet(new ServletHolder(new HelloServlet("Hello world")), "/users");
            handler.addServlet(new ServletHolder(new UserListServlet(te,userDao)), "/userlist");
            MessageDao md = new MessageSqlDao(conn);
            MessageService ms = new MessageService(md);
            handler.addServlet(new ServletHolder(new MessageServlet(te, ms)), "/messages/*");

            server.setHandler(handler);
            server.start();
            server.join();

        } catch (SQLException e) {
            System.out.println("Помилка підключення:");
            e.printStackTrace();
        }
    }
}
