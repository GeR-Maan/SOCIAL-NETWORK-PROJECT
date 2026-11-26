package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.exceptions.PostNotFoundException;
import tms.c42onl.socialnetworkproject.models.AuthorView;
import tms.c42onl.socialnetworkproject.models.Comment;
import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.PostImage;
import tms.c42onl.socialnetworkproject.storages.PostImageStorage;
import tms.c42onl.socialnetworkproject.models.PostPage;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import tms.c42onl.socialnetworkproject.service.ReactionService;
import tms.c42onl.socialnetworkproject.storages.PostDetailsStorage;
import java.util.List;
import static tms.c42onl.socialnetworkproject.models.reactions.ReactionType.NOTHING;

public class DefaultPostDetailsService implements tms.c42onl.socialnetworkproject.services.PostDetailsService {

    private final PostDetailsStorage storage;
    private final JDBCManager jdbcManager;
    private final PostImageStorage imageStorage;
    private final tms.c42onl.socialnetworkproject.services.CommentService commentService;
    private final ReactionService reactionService;

    public DefaultPostDetailsService(PostDetailsStorage storage,
                                     JDBCManager jdbcManager,
                                     PostImageStorage imageStorage,
                                     tms.c42onl.socialnetworkproject.services.CommentService commentService,
                                     ReactionService reactionService) {
        this.storage = storage;
        this.jdbcManager = jdbcManager;
        this.imageStorage = imageStorage;
        this.commentService = commentService;
        this.reactionService = reactionService;
    }

    @Override
    public PostPage getPostDetails(long postId, Long currentUserId) {
        PostDetailsStorage.PostRow post = storage.findPostById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        AuthorView author = storage.findAuthorByUserId(post.authorId)
                .orElseGet(() -> new AuthorView(post.authorId, "Пользователь", null));

        List<PostImage> photos = jdbcManager.executeWithResult(
                connection -> imageStorage.findByPostId(connection, postId)
        );
        List<Comment> comments = commentService.getCommentsByPostId(postId);

        ReactionCount reactions = reactionService.getCounters(postId);
        ReactionType myReaction = (currentUserId == null)
                ? NOTHING
                : reactionService.getUserReaction(currentUserId, postId);

        return new PostPage(
                post.id,
                author,
                post.createdAt,
                post.text,
                photos,
                comments,
                reactions,
                myReaction,
                currentUserId != null
        );
    }
}