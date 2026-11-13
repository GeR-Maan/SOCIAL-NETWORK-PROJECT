package tms.c42onl.socialnetworkproject.storages;

import java.util.List;

public interface PostCardStorage {
    List<PostCardRow> findAuthorById(long authorId);
}
