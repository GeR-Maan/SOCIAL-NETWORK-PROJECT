package tms.c42onl.socialnetworkproject.service;

import tms.c42onl.socialnetworkproject.models.PostCard;
import tms.c42onl.socialnetworkproject.storages.PostCardStorage;

import java.util.List;

public class PostCardService {
    private final PostCardStorage postCardStorage;

    public PostCardService(PostCardStorage postCardStorage) {
        this.postCardStorage = postCardStorage;
    }

    public List<PostCard> getAuthorPosts(long authorId){
        var rows = postCardStorage.findAuthorById(authorId);

        return rows.stream().map(row -> new PostCard(
                row.id(),
                row.text(),
                row.createdAt(),
                "/post?id=" + row.id(),
                new PostCard.AuthorView(
                        row.authorId(),
                        row.authorDisplayName(),
                        row.authorAvatarUrl()
                ),
                List.of(),
                new PostCard.Counters(0, 0)
        )).toList();
    }
}
