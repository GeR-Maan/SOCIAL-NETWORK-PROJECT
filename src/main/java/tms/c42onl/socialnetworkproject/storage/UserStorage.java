package tms.c42onl.socialnetworkproject.storage;

import tms.c42onl.socialnetworkproject.model.User;

import java.util.Optional;

public interface UserStorage {
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    void save(User user);
}
