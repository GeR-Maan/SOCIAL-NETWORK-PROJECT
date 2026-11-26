package tms.c42onl.socialnetworkproject.models;
import java.time.LocalDateTime;
import java.util.List;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;

public class PostPage {

    private final long postId;
    private final AuthorView author;
    private final LocalDateTime createdAt;
    private final String text;

    private final List<PostImage> photos;
    private final List<Comment> comments;
    private final ReactionCount reactions;
    private final ReactionType currentUserReaction;
    private final boolean currentUserLoggedIn;

    public PostPage(long postId, AuthorView author, LocalDateTime createdAt, String text,
                    List<PostImage> photos, List<Comment> comments,
                    ReactionCount reactions, ReactionType currentUserReaction, boolean currentUserLoggedIn) {
        this.postId = postId;
        this.author = author;
        this.createdAt = createdAt;
        this.text = text;
        this.photos = photos;
        this.comments = comments;
        this.reactions = reactions;
        this.currentUserReaction = currentUserReaction;
        this.currentUserLoggedIn = currentUserLoggedIn;
    }

    public long getPostId() { return postId; }
    public AuthorView getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getText() { return text; }
    public List<PostImage> getPhotos() { return photos; }
    public List<Comment> getComments() { return comments; }
    public ReactionCount getReactions() { return reactions; }
    public ReactionType getCurrentUserReaction() {
        return currentUserReaction;
    }
    public boolean isCurrentUserLoggedIn() { return currentUserLoggedIn; }
}
