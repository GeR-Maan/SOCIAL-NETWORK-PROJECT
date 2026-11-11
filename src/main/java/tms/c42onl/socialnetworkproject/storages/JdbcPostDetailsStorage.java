package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.config.ConnectionProvider;
import tms.c42onl.socialnetworkproject.models.AuthorView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class JdbcPostDetailsStorage implements PostDetailsStorage {

    private final ConnectionProvider connections;

    public JdbcPostDetailsStorage(ConnectionProvider connections) {
        this.connections = connections;
    }

    @Override
    public Optional<PostRow> findPostById(long postId) {
        final String sql = "SELECT id, author_id, text, created_at FROM posts WHERE id = ?";

        try (Connection c = connections.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(new PostRow(
                        rs.getLong("id"),
                        rs.getLong("author_id"),
                        rs.getString("text"),
                        rs.getObject("created_at", LocalDateTime.class)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить пост по id " + postId, e);
        }
    }

    @Override
    public Optional<AuthorView> findAuthorByUserId(long userId) {
        // avatar_url = null, пока TMS-17/30 не согласуют поле.
        final String sql = "SELECT id, first_name, last_name FROM users WHERE id = ?";

        try (Connection c = connections.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String displayName = ((firstName == null ? "" : firstName) + " " +
                        (lastName == null ? "" : lastName)).trim();
                if (displayName.isEmpty()) {
                    displayName = "Пользователь";
                }
                return Optional.of(new AuthorView(rs.getLong("id"), displayName, null));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить автора по id " + userId, e);
        }
    }
}