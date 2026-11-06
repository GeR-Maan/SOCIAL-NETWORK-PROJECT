package tms.c42onl.socialnetworkproject.Services;
import tms.c42onl.socialnetworkproject.Exceptions.PostNotFoundException;
import tms.c42onl.socialnetworkproject.models.AuthorView;
import tms.c42onl.socialnetworkproject.models.PostPage;
import tms.c42onl.socialnetworkproject.storages.PostDetailsStorage;
import java.util.List;

public class DefaultPostDetailsService implements PostDetailsService {

    private final PostDetailsStorage storage;
    private final PhotoService photoService;         // TMS-17
    private final CommentService commentService;     // TMS-22
    private final ReactionService reactionService;   // TMS-23

    public DefaultPostDetailsService(PostDetailsStorage storage,
                                     PhotoService photoService,
                                     CommentService commentService,
                                     ReactionService reactionService) {
        this.storage = storage;
        this.photoService = photoService;
        this.commentService = commentService;
        this.reactionService = reactionService;
    }

    @Override
    public PostPage getPostDetails(long postId, Long currentUserId) {
        PostDetailsStorage.PostRow post = storage.findPostById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        AuthorView author = storage.findAuthorByUserId(post.authorId)
                .orElseGet(() -> new AuthorView(post.authorId, "Пользователь", null));

        List<PhotoView> photos = photoService.getPhotosByPostId(postId);
        List<CommentView> comments = commentService.getCommentsByPostId(postId);
        ReactionSummary reactions = reactionService.getReactionsForPost(postId, currentUserId);

        return new PostPage(
                post.id, author, post.createdAt, post.text,
                photos, comments, reactions,
                currentUserId != null
        );
    }
}
