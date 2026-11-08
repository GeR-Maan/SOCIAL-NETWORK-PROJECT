package tms.c42onl.socialnetworkproject.storages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;

import java.util.ArrayList;

import tms.c42onl.socialnetworkproject.models.Comment;
import java.util.List;
import tms.c42onl.socialnetworkproject.config.ConnectionProvider;

public class JdbcCommentStorage implements CommentStorage {

    private final ConnectionProvider connectionProvider;

    public JdbcCommentStorage(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void addComment(Comment comment) {

        String sql = """
            
            
            INSERT INTO comments (post_id, author_id, text)
            VALUES (?, ?, ?)
            """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, comment.getPostId());
            statement.setLong(2, comment.getAuthorId());
            statement.setString(3, comment.getText());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении комментария", e);
        }

    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {

        String sql = """
            SELECT id, post_id, author_id, text, created_at
            FROM comments
            WHERE post_id = ?
            ORDER BY created_at ASC, id ASC
            """;

        List<Comment> comments = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, postId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getLong("id"),
                        resultSet.getLong("post_id"),
                        resultSet.getString("text"),
                        resultSet.getLong("author_id"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );

                comments.add(comment);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении комментариев", e);
        }

        return comments;
    }

    @Override
    public int countCommentsByPostId(Long postId) {
        String sql = """
            SELECT COUNT(*)
            FROM comments
            WHERE post_id = ?
            """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, postId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подсчёте комментариев", e);
        }

        return 0;
    }
}
