package tms.c42onl.socialnetworkproject.models;
import java.time.LocalDateTime;
import java.util.List;

public class PostPage {

    private final long postId;
    private final AuthorView author;
    private final LocalDateTime createdAt;
    private final String text;

    private final List<PhotoView> photos;
    private final List<CommentView> comments;
    private final ReactionSummary reactions;
    private final boolean currentUserLoggedIn;

    public PostPage(long postId, AuthorView author, LocalDateTime createdAt, String text,
                    List<PhotoView> photos, List<CommentView> comments,
                    ReactionSummary reactions, boolean currentUserLoggedIn) {
        this.postId = postId;
        this.author = author;
        this.createdAt = createdAt;
        this.text = text;
        this.photos = photos;
        this.comments = comments;
        this.reactions = reactions;
        this.currentUserLoggedIn = currentUserLoggedIn;
    }

    public long getPostId() { return postId; }
    public AuthorView getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getText() { return text; }
    public List<PhotoView> getPhotos() { return photos; }
    public List<CommentView> getComments() { return comments; }
    public ReactionSummary getReactions() { return reactions; }
    public boolean isCurrentUserLoggedIn() { return currentUserLoggedIn; }
}
