package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.User;
import tms.c42onl.socialnetworkproject.models.PublicUser;

import java.util.Optional;

public interface UserStorage {
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    Optional<PublicUser> findPublicUserById(Long id);
    void save(User user);
}