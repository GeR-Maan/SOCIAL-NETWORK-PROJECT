package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.models.User;
import tms.c42onl.socialnetworkproject.exceptions.RegistrationException;
import tms.c42onl.socialnetworkproject.services.UserService;
//import tms.c42onl.socialnetworkproject.storages.JbdcUserStorage;
//import tms.c42onl.socialnetworkproject.storages.UserStorage;
//import tms.c42onl.socialnetworkproject.storages.JbdcUserStorage;
//import tms.c42onl.socialnetworkproject.storages.UserStorage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/reg")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
//        TODO: TMS-32
//        JDBCManager jdbcManager = (JDBCManager) getServletContext().getAttribute("connection");
//        UserStorage userStorage = new JbdcUserStorage(jdbcManager);
//        this.userService = new UserService(userStorage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/pages/reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDateStr = req.getParameter("birthDate");

        LocalDate birthDate = null;
        try {
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                birthDate = LocalDate.parse(birthDateStr);
            }
        } catch (DateTimeParseException e) {
            User invalidUser = new User(null, login, password, firstName, lastName, null);
            throw new RegistrationException("Incorrect date format. Please select a date from the calendar.", invalidUser);
        }


        try {
            userService.registerUser(login, password, firstName, lastName, birthDate);
            resp.sendRedirect(req.getContextPath() + "/log");
        } catch (RegistrationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("invalidUser", e.getInvalidUser());

            req.getRequestDispatcher("/pages/reg.jsp").forward(req, resp);
        }
    }
}
