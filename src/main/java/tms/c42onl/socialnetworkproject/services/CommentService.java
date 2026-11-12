package tms.c42onl.socialnetworkproject.services;

import java.util.List;
import tms.c42onl.socialnetworkproject.models.Comment;
import tms.c42onl.socialnetworkproject.storages.CommentStorage;

public class CommentService {

    private final CommentStorage commentStorage;

    public CommentService(CommentStorage commentStorage) {
        this.commentStorage = commentStorage;
    }
    public void addComment(Comment comment) {

        if (comment == null) {
            throw new IllegalArgumentException("Комментарий не может быть null");
        }

        if (comment.getText() == null || comment.getText().isBlank()) {
            throw new IllegalArgumentException("Комментарий не может быть пустым");
        }

        commentStorage.addComment(comment);
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentStorage.getCommentsByPostId(postId);
    }
    public int countCommentsByPostId(Long postId) {
        return commentStorage.countCommentsByPostId(postId);
    }
}
