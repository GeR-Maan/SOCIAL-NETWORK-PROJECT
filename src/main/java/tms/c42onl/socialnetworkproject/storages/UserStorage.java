package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.User;

import java.util.Optional;

public interface UserStorage {
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    void save(User user);
}
