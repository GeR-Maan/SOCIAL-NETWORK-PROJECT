
package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.PublicUser;
import tms.c42onl.socialnetworkproject.services.UserService;
import tms.c42onl.socialnetworkproject.storages.JbdcUserStorage;
import tms.c42onl.socialnetworkproject.storages.UserStorage;

import java.io.IOException;

@WebServlet({"/profile", "/profile/me"})
public class ProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();

        JDBCManager jdbcManager =
                (JDBCManager) getServletContext().getAttribute("connection");

        if (jdbcManager == null) {
            throw new ServletException("JDBCManager не найден в ServletContext");
        }

        UserStorage userStorage = new JbdcUserStorage(jdbcManager);
        this.userService = new UserService(userStorage);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId;

        if ("/profile/me".equals(request.getServletPath())) {

            PublicUser currentUser = (PublicUser) request.getAttribute("currentUser");

            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            userId = currentUser.getId();
        } else {
            String idParameter = request.getParameter("id");

            if (idParameter == null || idParameter.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID пользователя");
                return;
            }
            try {
                userId = Long.parseLong(idParameter);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID пользователя");
                return;
            }
            if (userId <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID пользователя должен быть положительным");
                return;
            }
        }

        PublicUser profile = userService.getPublicUserById(userId);

        if (profile == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND, "Пользователь не найден");
            return;
        }
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/WEB-IN/views/profile.jsp")
                .forward(request, response);
    }
}
