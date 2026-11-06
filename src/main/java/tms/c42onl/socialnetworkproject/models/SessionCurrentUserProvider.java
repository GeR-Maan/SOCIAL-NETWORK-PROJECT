package tms.c42onl.socialnetworkproject.models;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class SessionCurrentUserProvider implements CurrentUserProvider {

    public static final String SESSION_USER_KEY = "userId";

    private final HttpServletRequest request;

    public SessionCurrentUserProvider(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public PublicUser getCurrentUser() {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        Object attribute = session.getAttribute(SESSION_USER_KEY);

        if (attribute instanceof Long userId) {
            return null;
        }

        return null;
    }
}
