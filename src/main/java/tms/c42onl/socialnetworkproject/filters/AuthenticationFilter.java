
package tms.c42onl.socialnetworkproject.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.PublicUser;
import tms.c42onl.socialnetworkproject.models.UserProfileService;
import tms.c42onl.socialnetworkproject.models.SessionCurrentUserProvider;
import tms.c42onl.socialnetworkproject.services.UserService;
import tms.c42onl.socialnetworkproject.storages.JbdcUserStorage;
import tms.c42onl.socialnetworkproject.storages.UserStorage;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private UserProfileService userProfileService;

    @Override
    public void init(FilterConfig config) throws ServletException {

        JDBCManager jdbcManager =
                (JDBCManager) config.getServletContext()
                        .getAttribute("connection");

        if (jdbcManager == null) {
            throw new ServletException(
                    "JDBCManager не найден в ServletContext");
        }

        UserStorage userStorage = new JbdcUserStorage(jdbcManager);

        this.userProfileService = new UserService(userStorage);
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        PublicUser currentUser =
                new SessionCurrentUserProvider(
                        request, userProfileService
                ).getCurrentUser();

        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isAuthenticated", currentUser != null);

        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        if (requiresAuth(request, path) && currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean requiresAuth(HttpServletRequest request, String path) {

        return java.util.Set.of(
                "/posts/create",
                "/comments/create",
                "/reactions/toggle",
                "/subscriptions/toggle",
                "/subscriptions/feed",
                "/profile/me",
                "/logout"
        ).contains(path);
    }
}
