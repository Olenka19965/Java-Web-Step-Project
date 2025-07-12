package com.tinder;

import com.tinder.controller.*;
import com.tinder.dao.message.MessageDao;
import com.tinder.dao.message.MessageSqlDao;
import com.tinder.dao.model.SqlUserLikeDao;
import com.tinder.dao.model.SqlUserProfileDao;
import com.tinder.dao.model.UserLikeDao;
import com.tinder.service.*;
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
            UserProfileService userService = new UserProfileServiceImpl(userDao);
            UserLikeDao likeDao = new SqlUserLikeDao(conn);
            LikeService likeService = new LikeServiceImpl(likeDao, userDao);
            MessageDao messageDao = new MessageSqlDao(conn);
            MessageService messageService = new MessageService(messageDao);
            ServletContextHandler handler = new ServletContextHandler();
            handler.addServlet(new ServletHolder(new HelloServlet("Hello world")), "/users");
            handler.addServlet(new ServletHolder(new UserListServlet(te, userService, likeService)), "/userlist");
            handler.addServlet(new ServletHolder(new MessageServlet(te, messageService)), "/messages/*");
            handler.addServlet(new ServletHolder(new LikedServlet(te, userService, likeService)), "/liked");
            server.setHandler(handler);
            server.start();
            server.join();

        } catch (SQLException e) {
            System.out.println("Помилка підключення:");
            e.printStackTrace();
        }
    }
}
