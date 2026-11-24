package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.Comment;
import java.util.List;

public interface CommentStorage {

    void addComment(Comment comment);

    List<Comment> getCommentsByPostId(Long postId);

    int countCommentsByPostId(Long postId);

}
