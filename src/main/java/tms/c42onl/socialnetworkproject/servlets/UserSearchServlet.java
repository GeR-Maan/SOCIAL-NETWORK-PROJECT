package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.services.UserSearchService;

import java.io.IOException;
import java.util.List;

@WebServlet("/users/search")
public class UserSearchServlet extends HttpServlet {
    private UserSearchService userSearchService;

    @Override
    public void init() throws ServletException {
        userSearchService = (UserSearchService) getServletContext().getAttribute("userSearchService");
        if(userSearchService == null) {
            throw new ServletException("UserSearchService не инициализирован");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String query = req.getParameter("q");
        req.setAttribute("query", query);
        try {
            req.setAttribute("users", userSearchService.search(query));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("error", e.getMessage());
            req.setAttribute("users", List.of());
        }


        req.getRequestDispatcher("/pages/user-search.jsp").forward(req, resp);
    }
}
