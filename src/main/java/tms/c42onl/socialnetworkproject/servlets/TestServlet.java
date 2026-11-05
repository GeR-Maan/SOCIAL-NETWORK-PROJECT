package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.config.ConnectionProvider;
import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.config.JDBCManagerDemo;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JDBCManager connection = (JDBCManager) getServletContext().getAttribute("connection");
        ConnectionProvider provider = ((ConnectionProvider) getServletContext().getAttribute("provider"));

        JDBCManagerDemo managerDemo = new JDBCManagerDemo(connection, provider);

        try {
            managerDemo.testJDBCConnection();
            managerDemo.testTransaction();

            resp.getWriter().println("tests completed");
        } catch (SQLException e) {
            resp.getWriter().println("tests do not completed");
            //throw new RuntimeException(e);
        }
    }
}
