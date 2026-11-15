package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tms.c42onl.socialnetworkproject.models.SessionCurrentUserProvider;

import java.io.IOException;

@WebServlet("/dev-login")
public class DevLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if ("true".equals(request.getParameter("logout"))) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        } else {
            String raw = request.getParameter("userId");
            long userId = (raw == null || raw.isBlank()) ? 1L : Long.parseLong(raw);
            request.getSession(true)
                    .setAttribute(SessionCurrentUserProvider.SESSION_USER_KEY, userId);
        }
        response.sendRedirect(request.getContextPath() + "/home");
    }
}