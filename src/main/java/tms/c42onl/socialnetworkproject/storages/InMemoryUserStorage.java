package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserStorage implements UserStorage {
    private final Map<String, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public boolean existsByLogin(String login) {
        return storage.containsKey(login);
    }

    @Override
    public void save(User user) {
        user.setId(idCounter.incrementAndGet());
        storage.put(user.getLogin(), user);
    }
}
