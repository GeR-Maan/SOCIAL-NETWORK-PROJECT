package tms.c42onl.socialnetworkproject.models;

import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private Long postId;
    private Long authorId;
    private String text;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Comment(Long id, Long postId, String text, Long authorId, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.text = text;
        this.authorId = authorId;
        this.createdAt = createdAt;


    }
}
