package tms.c42onl.socialnetworkproject.models;


import java.util.Map;

public class InMemoryUserProfileService implements UserProfileService {

    private static final Map<Long, PublicUser> USERS = Map.of(
            1L, new PublicUser(1L, "alice", "Алиса"),
            2L, new PublicUser(2L, "bob",   "Боб")
    );

    @Override
    public PublicUser getPublicUserById(Long userId) {

        if (userId == null) {
            return null;
        }

        return USERS.getOrDefault(
                userId,
                new PublicUser(userId, "user_" + userId, "Пользователь " + userId)
        );
    }
}