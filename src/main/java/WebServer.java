import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.Connection;
import java.sql.SQLException;

public class WebServer {
    public static void main(String[] args) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            System.out.println("Підключення успішне!");
        } catch (SQLException e) {
            System.out.println("Помилка підключення:");
            e.printStackTrace();
        }
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(handler);
        HelloServlet helloServlet = new HelloServlet("Hello world");
        handler.addServlet(new ServletHolder(helloServlet), "/users");
        server.start();
        server.join();
    }
}
