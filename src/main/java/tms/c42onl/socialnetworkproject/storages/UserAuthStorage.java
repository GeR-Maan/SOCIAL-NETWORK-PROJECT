package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.User;

import java.util.Optional;

public interface UserAuthStorage {
    Optional<User> findByLogin(String login);
}
