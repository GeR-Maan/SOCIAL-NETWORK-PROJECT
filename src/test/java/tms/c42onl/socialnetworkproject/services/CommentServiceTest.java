package tms.c42onl.socialnetworkproject.services;

import org.junit.jupiter.api.Test;
import tms.c42onl.socialnetworkproject.models.Comment;
import tms.c42onl.socialnetworkproject.storages.CommentStorage;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommentServiceTest {

    @Test
    void shouldAddCommentWhenCommentIsValid() {

        // 1. Создаём имитацию хранилища
        CommentStorage commentStorage = mock(CommentStorage.class);

        // 2. Передаём хранилище в сервис
        CommentService commentService = new CommentService(commentStorage);

        // 3. Создаём корректный комментарий
        Comment comment = new Comment(
                1L,
                1L,
                "Это мой первый комментарий",
                1L,
                LocalDateTime.now()
        );

        // 4. Вызываем проверяемый метод
        commentService.addComment(comment);

        // 5. Проверяем, что комментарий передан в хранилище
        verify(commentStorage).addComment(comment);
    }

    @Test
    void shouldThrowExceptionWhenCommentIsNull() {
        CommentService commentService = new CommentService(null);

        assertThrows(IllegalArgumentException.class, () -> {
            commentService.addComment(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenCommentTextIsEmpty() {
        CommentService commentService = new CommentService(null);

        Comment comment = new Comment(
                1L,
                1L,
                "",
                1L,
                LocalDateTime.now()
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> commentService.addComment(comment)
        );

        assertEquals("Комментарий не может быть пустым", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenCommentTextIsBlank() {
        CommentService commentService = new CommentService(null);

        Comment comment = new Comment(
                1L,
                1L,
                "   ",
                1L,
                LocalDateTime.now()
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> commentService.addComment(comment)
        );

        assertEquals("Комментарий не может быть пустым", exception.getMessage());
    }
}