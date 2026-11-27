package tms.c42onl.socialnetworkproject.models;

import java.time.LocalDateTime;
import java.util.List;

public class PostCard {

    private final long postId;
    private final AuthorView author;
    private final LocalDateTime createdAt;
    private final String text;
    private final List<PostImage> photos;
    private final long likeCount;
    private final long dislikeCount;
    private final long commentCount;

    public PostCard(long postId, AuthorView author, LocalDateTime createdAt, String text,
                    List<PostImage> photos, long likeCount, long dislikeCount, long commentCount) {
        this.postId = postId;
        this.author = author;
        this.createdAt = createdAt;
        this.text = text;
        this.photos = photos;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.commentCount = commentCount;
    }

    public long getPostId() {
        return postId;
    }

    public AuthorView getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public List<PostImage> getPhotos() {
        return photos;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }
}
