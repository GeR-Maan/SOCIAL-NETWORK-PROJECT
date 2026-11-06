package tms.c42onl.socialnetworkproject.Exceptions;

public class PostNotFoundException extends RuntimeException {
    private final long postId;
    public PostNotFoundException(long postId) {
        super("Post not found: " + postId);
        this.postId = postId;
    }
    public long getPostId() { return postId; }
}