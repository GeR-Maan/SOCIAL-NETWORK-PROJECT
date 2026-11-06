package tms.c42onl.socialnetworkproject.storages;
import tms.c42onl.socialnetworkproject.models.AuthorView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class JdbcPostDetailsStorage implements PostDetailsStorage {

    private final ConnectionProvider connections;
    private final SqlExceptionTranslator sqlErrors;

    public JdbcPostDetailsStorage(ConnectionProvider connections, SqlExceptionTranslator sqlErrors) {
        this.connections = connections;
        this.sqlErrors = sqlErrors;
    }

    @Override
    public Optional<PostRow> findPostById(long postId) {
        final String sql = "SELECT id, author_id, text, created_at FROM posts WHERE id = ?";
        try (Connection c = connections.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new PostRow(
                        rs.getLong("id"),
                        rs.getLong("author_id"),
                        rs.getString("text"),
                        rs.getObject("created_at", LocalDateTime.class)
                ));
            }
        } catch (SQLException e) {
            throw sqlErrors.translate("findPostById", e);
        }
    }

    @Override
    public Optional<AuthorView> findAuthorByUserId(long userId) {
        final String sql = "SELECT id, display_name, avatar_url FROM users WHERE id = ?";
        try (Connection c = connections.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new AuthorView(
                        rs.getLong("id"),
                        rs.getString("display_name"),
                        rs.getString("avatar_url")
                ));
            }
        } catch (SQLException e) {
            throw sqlErrors.translate("findAuthorByUserId", e);
        }
    }
}
