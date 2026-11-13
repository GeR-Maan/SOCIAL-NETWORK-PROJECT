package tms.c42onl.socialnetworkproject.storage;

import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.model.Subscription;
import tms.c42onl.socialnetworkproject.model.User;

import java.util.List;
import java.util.Optional;

public class JdbcSubscriptionStorage implements SubscriptionStorage{
    private final JDBCManager jdbcManager;

    public JdbcSubscriptionStorage(JDBCManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }

    @Override
    public void save(Subscription subscription) {

    }

    @Override
    public void delete(Long subscriberId, Long authorId) {

    }

    @Override
    public Optional<Subscription> findByUsers(Long subscriberId, Long authorId) {
        return Optional.empty();
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public int countSubscribers(Long authorId) {
        return 0;
    }

    @Override
    public List<User> findSubscribersByAuthorId(Long authorId) {
        return List.of();
    }

    @Override
    public List<Long> findAuthorIdBySubscriberId(Long subscriberId) {
        return List.of();
    }
}
