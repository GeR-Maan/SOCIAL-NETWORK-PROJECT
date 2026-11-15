package tms.c42onl.socialnetworkproject.storage;

import tms.c42onl.socialnetworkproject.model.Subscription;
import tms.c42onl.socialnetworkproject.model.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionStorage {
    void save(Subscription subscription);

    void delete(Long subscriberId, Long authorId);

    Optional<Subscription> findByUsers(Long subscriberId, Long authorId);

    Optional<Subscription> findById(Long id);

    int countSubscribers(Long authorId);

    List<User> findSubscribersByAuthorId(Long authorId);

    List<Long> findAuthorIdBySubscriberId(Long subscriberId);
}
