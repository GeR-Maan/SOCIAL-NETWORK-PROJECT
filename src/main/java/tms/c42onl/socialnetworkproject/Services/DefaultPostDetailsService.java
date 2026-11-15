package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.exceptions.PostNotFoundException;
import tms.c42onl.socialnetworkproject.models.AuthorView;
import tms.c42onl.socialnetworkproject.models.Comment;
import tms.c42onl.socialnetworkproject.models.PhotoView;
import tms.c42onl.socialnetworkproject.models.PostPage;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import tms.c42onl.socialnetworkproject.service.ReactionService;
import tms.c42onl.socialnetworkproject.storages.PostDetailsStorage;
import java.util.List;
import static tms.c42onl.socialnetworkproject.models.reactions.ReactionType.NOTHING;

public class DefaultPostDetailsService implements PostDetailsService {

    private final PostDetailsStorage storage;
    private final PhotoService photoService;       // TMS-17
    private final CommentService commentService;   // TMS-22
    private final ReactionService reactionService; // TMS-23

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