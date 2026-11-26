package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.models.PostImage;
import tms.c42onl.socialnetworkproject.storages.PostImageStorage;
import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.Post;
import tms.c42onl.socialnetworkproject.storages.PostStorage;
import java.util.List;

/**
 * Создание поста авторизованным пользователем.
 *
 * <p>Проверяет, что у поста есть текст или хотя бы одно фото
 * также что фото не больше пяти. Затем в одной транзакции сохраняет пост
 * и передаёт фото для TMS-17.
 */

public class PostService {

    private static final int MAX_PHOTOS = 5;
    private final JDBCManager jdbcManager;
    private final PostStorage postStorage;
    private final PostImageStorage imageStorage;

    public PostService(JDBCManager jdbcManager, PostStorage postStorage, PostImageStorage imageStorage) {
        this.jdbcManager = jdbcManager;
        this.postStorage = postStorage;
        this.imageStorage = imageStorage;
    }

    public Long createPost(Long authorId, String text, List<PostImage> photos) {

        boolean noText = text == null || text.isBlank();
        if (noText && photos.isEmpty()) {
            throw new IllegalArgumentException("Добавьте текст или хотя бы одно фото");
        }
        if (photos.size() > MAX_PHOTOS) {
            throw new IllegalArgumentException("Можно прикрепить не больше " + MAX_PHOTOS + " фото");
        }

        Post post = new Post();
        post.setAuthorId(authorId);
        post.setText(text);

        return jdbcManager.executeWithResult(connection -> {
            Long postId = postStorage.save(connection, post);
            imageStorage.save(connection, postId, photos);
            return postId;
        });
    }
}
