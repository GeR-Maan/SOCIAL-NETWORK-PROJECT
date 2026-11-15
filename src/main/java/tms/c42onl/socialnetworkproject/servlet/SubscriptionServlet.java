package tms.c42onl.socialnetworkproject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tms.c42onl.socialnetworkproject.service.SubscriptionService;

import java.io.IOException;

@WebServlet("/subs")
public class SubscriptionServlet extends HttpServlet {
    private SubscriptionService subscriptionService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.subscriptionService = (SubscriptionService) getServletContext().getAttribute("subscriptionService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long subscriberId = (Long) session.getAttribute("userId");

        String authorIdStr = request.getParameter("authorId");
        String action = request.getParameter("action");

        if (authorIdStr == null || authorIdStr.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID автора");
            return;
        }

        Long authorId;
        try {
            authorId = Long.parseLong(authorIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID автора");
            return;
        }

        try {
            if ("subscribe".equals(action)) {
                subscriptionService.subscribe(subscriberId, authorId);
            } else if ("unsubscribe".equals(action)) {
                subscriptionService.unsubscribe(subscriberId, authorId);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/profile?id=" + authorId);

        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/profile?id=" + authorId + "&error=" + e.getMessage());
        }
    }
}
