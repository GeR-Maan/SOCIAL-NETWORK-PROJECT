package tms.c42onl.socialnetworkproject.models;

public class InMemoryUserProfileService implements UserProfileService {

    @Override
    public PublicUser getPublicUserById(Long userId) {

        if (userId == null) {
            return null;
        }

        return new PublicUser(
                userId,
                "user_" + userId,
                "Пользователь " + userId
        );
    }
}