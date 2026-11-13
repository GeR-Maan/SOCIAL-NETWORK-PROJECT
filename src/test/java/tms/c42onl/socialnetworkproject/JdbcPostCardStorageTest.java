package tms.c42onl.socialnetworkproject;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tms.c42onl.socialnetworkproject.execptions.execptionTranslator.PostgresSqlExceptionTranslator;
import tms.c42onl.socialnetworkproject.execptions.execptionTranslator.SqlExceptionTranslator;
import tms.c42onl.socialnetworkproject.shared.jdbc.ConnectionProvider;
import tms.c42onl.socialnetworkproject.storages.JdbcPostCardStorage;
import tms.c42onl.socialnetworkproject.storages.PostCardRow;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class JdbcPostCardStorageTest {

    private static JdbcDataSource dataSource;

    @BeforeAll
    static void setUpDb() throws SQLException {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:posts;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");

        // ВРЕМЕННО: до TMS-21/25. Реальные миграции users/posts — не наши.
        try (Connection c = dataSource.getConnection();
             Statement s = c.createStatement()) {
            s.execute("""
                CREATE TABLE users (
                    id BIGINT PRIMARY KEY,
                    display_name VARCHAR(255),
                    avatar_url VARCHAR(500)
                )
                """);
            s.execute("""
                CREATE TABLE posts (
                    id BIGINT PRIMARY KEY,
                    author_id BIGINT NOT NULL,
                    text TEXT,
                    created_at TIMESTAMP NOT NULL
                )
                """);
        }
    }

    @BeforeEach
    void cleanData() throws SQLException {
        try (Connection c = dataSource.getConnection();
             Statement s = c.createStatement()) {
            s.execute("DELETE FROM posts");
            s.execute("DELETE FROM users");
        }
    }

    @Test
    void returnsOnlyPostsOfGivenAuthor() throws SQLException {
        insertUser(1, "Alice", null);
        insertUser(2, "Bob", null);
        insertPost(10, 1, "alice-1", LocalDateTime.parse("2024-01-01T10:00:00"));
        insertPost(11, 1, "alice-2", LocalDateTime.parse("2024-01-02T10:00:00"));
        insertPost(20, 2, "bob-1",   LocalDateTime.parse("2024-01-03T10:00:00"));

        var rows = newStorage().findAuthorById(1);

        assertEquals(2, rows.size());
        assertTrue(rows.stream().allMatch(r -> r.authorId() == 1));
        assertFalse(rows.stream().anyMatch(r -> r.id() == 20L));
    }

    @Test
    void ordersNewestFirst() throws SQLException {
        insertUser(1, "Alice", null);
        insertPost(10, 1, "old", LocalDateTime.parse("2024-01-01T10:00:00"));
        insertPost(11, 1, "new", LocalDateTime.parse("2024-01-03T10:00:00"));
        insertPost(12, 1, "mid", LocalDateTime.parse("2024-01-02T10:00:00"));

        var rows = newStorage().findAuthorById(1);

        assertEquals(List.of(11L, 12L, 10L),
                rows.stream().map(PostCardRow::id).toList());
    }

    @Test
    void tiesBrokenByIdDesc() throws SQLException {
        insertUser(1, "Alice", null);
        LocalDateTime same = LocalDateTime.parse("2024-01-01T10:00:00");
        insertPost(10, 1, "a", same);
        insertPost(11, 1, "b", same);

        var rows = newStorage().findAuthorById(1);

        assertEquals(List.of(11L, 10L),
                rows.stream().map(PostCardRow::id).toList());
    }

    @Test
    void emptyForUnknownAuthor() {
        var rows = newStorage().findAuthorById(999);

        assertTrue(rows.isEmpty());
        assertNotNull(rows);
    }

    private JdbcPostCardStorage newStorage() {
        ConnectionProvider cp = dataSource::getConnection;
        SqlExceptionTranslator tr = new PostgresSqlExceptionTranslator();
        return new JdbcPostCardStorage(cp, tr);
    }

    private void insertUser(long id, String displayName, String avatarUrl) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO users (id, display_name, avatar_url) VALUES (?, ?, ?)")) {
            ps.setLong(1, id);
            ps.setString(2, displayName);
            ps.setString(3, avatarUrl);
            ps.executeUpdate();
        }
    }

    private void insertPost(long id, long authorId, String text, LocalDateTime createdAt)
            throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO posts (id, author_id, text, created_at) VALUES (?, ?, ?, ?)")) {
            ps.setLong(1, id);
            ps.setLong(2, authorId);
            ps.setString(3, text);
            ps.setObject(4, createdAt);
            ps.executeUpdate();
        }
    }
}
