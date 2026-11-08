package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("") // Пустая строка означает точный корень "/" сайта
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }


        // Перенаправляем запрос внутрь WEB-INF на нашу JSP
        req.getRequestDispatcher("/pages/index.jsp").forward(req, resp);
    }
}