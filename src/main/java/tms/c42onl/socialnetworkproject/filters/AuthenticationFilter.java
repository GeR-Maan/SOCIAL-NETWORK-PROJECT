package tms.c42onl.socialnetworkproject.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.models.CurrentUserProvider;
import tms.c42onl.socialnetworkproject.models.PublicUser;
import tms.c42onl.socialnetworkproject.models.SessionCurrentUserProvider;

import java.io.IOException;
import java.util.Set;

public class AuthenticationFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/",
            "/home",
            "/login",
            "/register",
            "/posts",
            "/users"
    );

    private static final Set<String> PROTECTED_PATHS = Set.of(
            "/posts/create",
            "/comments/create",
            "/reactions/toggle",
            "/subscriptions/toggle",
            "/subscriptions/feed",
            "/profile/me",
            "/logout"
    );

    private static final String LOGIN_PAGE = "/login";

    @Override
    public void doFilter(
            jakarta.servlet.ServletRequest servletRequest,
            jakarta.servlet.ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        CurrentUserProvider userProvider = new SessionCurrentUserProvider(request);
        PublicUser currentUser = userProvider.getCurrentUser();

        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isAuthenticated", currentUser != null);

        String path = request.getRequestURI().substring(
                request.getContextPath().length()
        );

        if (isProtected(request, path) && currentUser == null) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isProtected(HttpServletRequest request, String path) {
        if (PROTECTED_PATHS.contains(path)) {
            return true;
        }
        return "POST".equalsIgnoreCase(request.getMethod())
                && (path.equals("/posts") || path.equals("/comments"));
    }
}
