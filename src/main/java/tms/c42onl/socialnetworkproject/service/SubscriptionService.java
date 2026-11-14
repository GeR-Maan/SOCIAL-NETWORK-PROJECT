package tms.c42onl.socialnetworkproject.service;

import tms.c42onl.socialnetworkproject.model.Subscription;
import tms.c42onl.socialnetworkproject.model.User;
import tms.c42onl.socialnetworkproject.storage.SubscriptionStorage;

import java.awt.*;
import java.util.List;

public class SubscriptionService {
    private final SubscriptionStorage subscriptionStorage;

    public SubscriptionService(SubscriptionStorage subscriptionStorage) {
        this.subscriptionStorage = subscriptionStorage;
    }

    public void subscribe(Long subscriberId, Long authorId) {
        if (subscriberId.equals(authorId)) {
            throw new IllegalArgumentException("Пользователь не может подписаться на самого себя.");
        }

        if (subscriptionStorage.findByUsers(subscriberId, authorId).isPresent()) {
            throw new IllegalStateException("Пользователь уже подписан на данного автора.");
        }

        Subscription subscription = new Subscription(subscriberId, authorId);
        subscriptionStorage.save(subscription);
    }

    public void unsubscribe(Long subscriberId, Long authorId) {
        if (subscriptionStorage.findByUsers(subscriberId, authorId).isEmpty()) {
            throw new IllegalArgumentException("Подписка не найдена или уже была отменена.");
        }

        subscriptionStorage.delete(subscriberId, authorId);
    }

    public boolean isSubscribed(Long subscriberId, Long authorId) {
        if (subscriberId == null || authorId == null) {
            return false;
        }
        return subscriptionStorage.findByUsers(subscriberId, authorId).isPresent();
    }

    public int getSubscribersCount(Long authorId) {
        return subscriptionStorage.countSubscribers(authorId);
    }

    public List<User> getSubscribers(Long authorId) {
        return subscriptionStorage.findSubscribersByAuthorId(authorId);
    }

    public List<Long> getSubscriptionAuthorIds(Long subscriberId) {
        return subscriptionStorage.findAuthorIdBySubscriberId(subscriberId);
    }


}
