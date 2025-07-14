package com.tinder;

import com.tinder.controller.*;
import com.tinder.dao.auth.AuthDao;
import com.tinder.dao.auth.SqlAuthDao;
import com.tinder.dao.like.LIkeDao;
import com.tinder.dao.like.SqlLikeDao;
import com.tinder.dao.message.MessageDao;
import com.tinder.dao.message.SqlMessageDao;
import com.tinder.dao.user.SqlUserDao;
import com.tinder.dao.user.UserDao;
import com.tinder.service.auth.AuthService;
import com.tinder.service.like.LikeService;
import com.tinder.service.like.LikeServiceImpl;
import com.tinder.service.message.MessageService;
import com.tinder.service.user.UserService;
import com.tinder.service.user.UserServiceImpl;
import com.tinder.utils.DbUtil;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumSet;

public class WebServer {
    public static void main(String[] args) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            Flyway flyway = Flyway.configure()
                    .dataSource(DbUtil.getDataSource())
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();

            System.out.println("Підключення успішне!");
            Server server = new Server(8080);

            TemplateEngine te = new TemplateEngine();

            AuthDao authDao = new SqlAuthDao(conn);
            AuthService as = new AuthService(authDao);

            UserDao userDao = new SqlUserDao(conn);
            UserService userService = new UserServiceImpl(new SqlUserDao(conn));

            LIkeDao likeDao = new SqlLikeDao(conn);
            LikeService likeService = new LikeServiceImpl(likeDao, userDao);

            MessageDao messageDao = new SqlMessageDao(conn);
            MessageService messageService = new MessageService(messageDao);

            ServletContextHandler handler = new ServletContextHandler();

            handler.addServlet(new ServletHolder(new AssetServlet()), "/assets/*");

            handler.setSessionHandler(new SessionHandler());
            handler.addServlet(new ServletHolder(new RedirectServlet()), "/");
            handler.addServlet(new ServletHolder(new LoginServlet(te, as, userService)), "/login");
            handler.addFilter(new FilterHolder(new LoginFilter(te, as, userService)), "/*", EnumSet.of(DispatcherType.REQUEST));
            handler.addServlet(new ServletHolder(new HelloServlet("Hello world")), "/hello");
            handler.addServlet(new ServletHolder(new UserListServlet(te, userService, likeService)), "/users");
            handler.addServlet(new ServletHolder(new MessagesServlet(te, messageService, userService)), "/messages/*");
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
