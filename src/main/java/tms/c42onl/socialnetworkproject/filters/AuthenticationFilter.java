package tms.c42onl.socialnetworkproject.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.models.*;

import java.io.IOException;


public class AuthenticationFilter implements Filter {

    private UserProfileService userProfileService;

    @Override
    public void init(FilterConfig config) {
        this.userProfileService = new InMemoryUserProfileService(); // TODO: реальный сервис (TMS-20)
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        PublicUser currentUser =
                new SessionCurrentUserProvider(request, userProfileService).getCurrentUser();

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
                "/posts/create", "/comments/create", "/reactions/toggle",
                "/subscriptions/toggle", "/subscriptions/feed",
                "/profile/me", "/logout"
        ).contains(path);
    }
}