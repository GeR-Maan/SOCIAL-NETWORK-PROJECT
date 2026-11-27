package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.PostCard;
import tms.c42onl.socialnetworkproject.models.PostImage;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.service.ReactionService;
import tms.c42onl.socialnetworkproject.storages.PostFeedStorage;
import tms.c42onl.socialnetworkproject.storages.PostImageStorage;
import tms.c42onl.socialnetworkproject.services.CommentService;

import java.util.List;

public class DefaultPostFeedService implements PostFeedService {

    private final PostFeedStorage storage;
    private final JDBCManager jdbcManager;
    private final PostImageStorage imageStorage;
    private final ReactionService reactionService;
    private final CommentService commentService;

    public DefaultPostFeedService(PostFeedStorage storage,
                                  JDBCManager jdbcManager,
                                  PostImageStorage imageStorage,
                                  ReactionService reactionService,
                                  CommentService commentService) {
        this.storage = storage;
        this.jdbcManager = jdbcManager;
        this.imageStorage = imageStorage;
        this.reactionService = reactionService;
        this.commentService = commentService;
    }

    @Override
    public List<PostCard> getFeed() {
        List<PostCard> feed = storage.findFeed();

        return feed.stream().map(card -> {
            List<PostImage> photos = jdbcManager.executeWithResult(
                    connection -> imageStorage.findByPostId(connection, card.getPostId()));

            ReactionCount reactions = reactionService.getCounters(card.getPostId());

            long commentCount = commentService.countCommentsByPostId(card.getPostId());

            return new PostCard(
                    card.getPostId(),
                    card.getAuthor(),
                    card.getCreatedAt(),
                    card.getText(),
                    photos,
                    reactions.getCountLike(),
                    reactions.getCountDislike(),
                    commentCount
            );
        }).toList();
    }
}
