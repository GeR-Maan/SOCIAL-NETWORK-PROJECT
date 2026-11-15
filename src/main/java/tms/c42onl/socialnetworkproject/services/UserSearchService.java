package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.models.UserSearchResult;
import tms.c42onl.socialnetworkproject.storages.UserSearchStorage;

import java.util.List;

public class UserSearchService {
    private final UserSearchStorage userSearchStorage;

    public UserSearchService(UserSearchStorage userSearchStorage) {
        this.userSearchStorage = userSearchStorage;
    }

    public List<UserSearchResult> search(String query) {

        if (query == null || query.isBlank()) {
            return List.of();
        }
        String normalizedQuery = query.trim();
        if (normalizedQuery.length() > 100) {
            throw new IllegalArgumentException("Поисковый запрос слишком длинный");

        }
        return userSearchStorage.search(query);
    }


}
