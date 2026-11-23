package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.exceptions.AuthException;
import tms.c42onl.socialnetworkproject.models.User;
import tms.c42onl.socialnetworkproject.services.UserAuthService;
import tms.c42onl.socialnetworkproject.storages.JdbcUserAuthStorage;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // константы
    private static final String SESSION_USER_KEY = "userId";
    private static final int SESSION_TIMEOUT_SECONDS = 30 * 60; // 30 минут
//
    private UserAuthService authService;

    @Override
    public void init() {
        //  подключений из контекста (TMS-32)
        JDBCManager jdbcManager = (JDBCManager) getServletContext().getAttribute("connection");

        JdbcUserAuthStorage storage = new JdbcUserAuthStorage(jdbcManager);
//
        this.authService = new UserAuthService(storage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // пользователь уже авторизован (в сессии есть userId), кидаем на главную
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        // или страница входа
        req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
    }

    //существующая сессию (или null- если нет)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }


        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            // авторизация через сервис
            User user = authService.authenticate(login, password);

            // успех новая сессия для залогиненного
            session  = req.getSession(true);

            // контракт с TMS-28:  "userId" типа Long
            session.setAttribute(SESSION_USER_KEY, user.getId());

            //  время жизни сессии
            session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);


            resp.sendRedirect(req.getContextPath() + "/");

        } catch (AuthException e) {
            // ошибка возвращает на форму с сообщением об ошибке
            req.setAttribute("error", e.getMessage());
            req.setAttribute("loginValue", login); // сохранение введенный логин

            // возврат на страницу входа
            req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
        }
    }
}
