package tms.c42onl.socialnetworkproject.services;

import jakarta.servlet.http.Part;
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
    private final PostPhotoService photoService;

    public PostService(JDBCManager jdbcManager, PostStorage postStorage, PostPhotoService photoService) {
        this.jdbcManager = jdbcManager;
        this.postStorage = postStorage;
        this.photoService = photoService;
    }


    public Long createPost(Long authorId, String text, List<Part> photos) {

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

        jdbcManager.execute(connection -> {
            Long id = postStorage.save(connection, post);
            post.setId(id);
            photoService.save(connection, id, photos);
        });

        return post.getId();
    }
}
