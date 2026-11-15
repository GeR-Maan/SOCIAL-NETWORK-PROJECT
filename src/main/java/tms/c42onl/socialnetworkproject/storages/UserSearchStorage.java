package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.UserSearchResult;

import java.util.List;

public interface UserSearchStorage {
    List<UserSearchResult> search(String query);

}
