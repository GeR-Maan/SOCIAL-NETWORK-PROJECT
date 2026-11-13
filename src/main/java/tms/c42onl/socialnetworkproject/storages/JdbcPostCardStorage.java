package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.execptions.execptionTranslator.SqlExceptionTranslator;
import tms.c42onl.socialnetworkproject.shared.jdbc.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class JdbcPostCardStorage implements PostCardStorage{

    private final ConnectionProvider connections;
    private final SqlExceptionTranslator sqlErrors;

    public JdbcPostCardStorage(ConnectionProvider connections, SqlExceptionTranslator sqlErrors){
        this.connections = connections;
        this.sqlErrors = sqlErrors;
    }

    private static final String SELECT_BY_AUTHOR = """
            SELECT p.id            AS post_id,
                   p.author_id     AS author_id,
                   p.text          AS text,
                   p.created_at    AS created_at,
                   u.display_name  AS display_name,
                   u.avatar_url    AS avatar_url
            FROM posts p
            JOIN users u ON u.id = p.author_id
            WHERE p.author_id = ?
            ORDER BY p.created_at DESC, p.id DESC
            """;

    @Override
    public List<PostCardRow> findAuthorById(long authorId) {
        try (Connection c = connections.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_AUTHOR)){

            ps.setLong(1, authorId);

            try (ResultSet rs = ps.executeQuery()){
                List<PostCardRow> rows = new ArrayList<>();
                while (rs.next()) {
                    rows.add(new PostCardRow(
                            rs.getLong("post_id"),
                            rs.getLong("author_id"),
                            rs.getString("text"),
                            rs.getObject("created_at", LocalDateTime.class),
                            rs.getString("display_name"),
                            rs.getString("avatar_url")
                    ));
                    }
                    return rows;
                }
            }catch (SQLException e) {
            throw sqlErrors.translate("findByAuthorId",e);
        }
    }
}
