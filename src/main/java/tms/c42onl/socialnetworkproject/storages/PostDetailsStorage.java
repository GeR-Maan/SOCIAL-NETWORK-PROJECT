package tms.c42onl.socialnetworkproject.storages;
import tms.c42onl.socialnetworkproject.models.AuthorView;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PostDetailsStorage {

    Optional<PostRow> findPostById(long postId);

    Optional<AuthorView> findAuthorByUserId(long userId);

    final class PostRow {
        public final long id;
        public final long authorId;
        public final String text;
        public final LocalDateTime createdAt;

        public PostRow(long id, long authorId, String text, LocalDateTime createdAt) {
            this.id = id;
            this.authorId = authorId;
            this.text = text;
            this.createdAt = createdAt;
        }
    }
}