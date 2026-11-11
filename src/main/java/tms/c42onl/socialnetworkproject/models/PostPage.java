package tms.c42onl.socialnetworkproject.models;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import java.time.LocalDateTime;
import java.util.List;

public class PostPage {

    private final long postId;
    private final AuthorView author;
    private final LocalDateTime createdAt;
    private final String text;

    private final List<PhotoView> photos;               // TMS-17 (заглушка)
    private final List<Comment> comments;               // TMS-22
    private final ReactionCount reactions;              // TMS-23
    private final ReactionType currentUserReaction;     // TMS-23
    private final boolean currentUserLoggedIn;          // TMS-28

    public PostPage(long postId,
                    AuthorView author,
                    LocalDateTime createdAt,
                    String text,
                    List<PhotoView> photos,
                    List<Comment> comments,
                    ReactionCount reactions,
                    ReactionType currentUserReaction,
                    boolean currentUserLoggedIn) {
        this.postId = postId;
        this.author = author;
        this.createdAt = createdAt;
        this.text = text;
        this.photos = photos;
        this.comments = comments;
        this.reactions = reactions;
        this.currentUserReaction = (currentUserReaction == null)
                ? ReactionType.NOTHING
                : currentUserReaction;
        this.currentUserLoggedIn = currentUserLoggedIn;
    }

    public long getPostId() { return postId; }
    public AuthorView getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getText() { return text; }
    public List<PhotoView> getPhotos() { return photos; }
    public List<Comment> getComments() { return comments; }
    public ReactionCount getReactions() { return reactions; }
    public ReactionType getCurrentUserReaction() { return currentUserReaction; }
    public boolean isCurrentUserLoggedIn() { return currentUserLoggedIn; }
}
