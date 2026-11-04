package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.User;

public interface UserStorage {
    boolean existsByLogin(String login);
    void save(User user);
}
