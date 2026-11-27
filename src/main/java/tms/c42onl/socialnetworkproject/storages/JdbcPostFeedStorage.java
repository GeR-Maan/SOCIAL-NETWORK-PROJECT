package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.config.ConnectionProvider;
import tms.c42onl.socialnetworkproject.models.AuthorView;
import tms.c42onl.socialnetworkproject.models.PostCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostFeedStorage implements PostFeedStorage {

    private static final String FEED_SQL = """
        SELECT p.id,
               p.author_id,
               p.text,
               p.created_at,
               u.first_name,
               u.last_name
        FROM posts p
        JOIN users u ON u.id = p.author_id
        ORDER BY p.created_at DESC, p.id DESC
        """;

    private final ConnectionProvider connections;

    public JdbcPostFeedStorage(ConnectionProvider connections) {
        this.connections = connections;
    }

    @Override
    public List<PostCard> findFeed() {
        List<PostCard> feed = new ArrayList<>();

        try (Connection c = connections.getConnection();
             PreparedStatement ps = c.prepareStatement(FEED_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AuthorView author = new AuthorView(
                        rs.getLong("author_id"),
                        displayName(rs.getString("first_name"), rs.getString("last_name")),
                        null
                );

                feed.add(new PostCard(
                        rs.getLong("id"),
                        author,
                        rs.getObject("created_at", LocalDateTime.class),
                        rs.getString("text"),
                        List.of(),
                        0,
                        0,
                        0
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось загрузить общую ленту публикаций", e);
        }

        return feed;
    }

    private static String displayName(String firstName, String lastName) {
        String name = ((firstName == null ? "" : firstName) + " " +
                (lastName == null ? "" : lastName)).trim();
        return name.isEmpty() ? "Пользователь" : name;
    }
}
