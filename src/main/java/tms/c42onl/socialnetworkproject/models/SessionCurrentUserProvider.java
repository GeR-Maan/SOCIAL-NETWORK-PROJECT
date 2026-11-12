package tms.c42onl.socialnetworkproject.models;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionCurrentUserProvider implements CurrentUserProvider {

    public static final String SESSION_USER_KEY = "userId";

    private final HttpServletRequest request;
    private final UserProfileService userProfileService;

    public SessionCurrentUserProvider(HttpServletRequest request,
                                      UserProfileService userProfileService) {
        this.request = request;
        this.userProfileService = userProfileService;
    }

    @Override
    public PublicUser getCurrentUser() {

        // 1. Сессии нет -> гость.
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        Object attribute = session.getAttribute(SESSION_USER_KEY);

        // 2. В сессии не id -> гость.
        Long userId;
        if (attribute instanceof Long value) {
            userId = value;
        } else if (attribute instanceof Integer value) {
            userId = value.longValue();
        } else {
            return null;
        }

        // 3. id найден -> отдаём пользователя (без Optional).
        return userProfileService.getPublicUserById(userId);
    }
}