package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.UserSearchResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserSearchStorage implements UserSearchStorage {
    private static final String SEARCH_SQL = """
            SELECT id, login, first_name, last_name
            FROM users
            WHERE login ILIKE ?
            OR first_name ILIKE ?
            OR last_name ILIKE ?
            ORDER BY login, id
            LIMIT 50
            """;

    private final JDBCManager jdbcManager;

    public JdbcUserSearchStorage(JDBCManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }


    @Override
    public List<UserSearchResult> search(String query) {

        if (query == null || query.isBlank()) {
            return List.of();
        }
        String pattern = "%" + query.trim() + "%";
        List<UserSearchResult> results = new ArrayList<>();

        jdbcManager.execute(connection ->  {
            try (PreparedStatement statement = connection.prepareStatement(SEARCH_SQL)) {
                statement.setString(1, pattern);
                statement.setString(2, pattern);
                statement.setString(3, pattern);

                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        results.add(new UserSearchResult(rs.getLong("id"),
                                rs.getString("login"),
                                rs.getString("first_name"),
                                rs.getString("last_name")));
                    }
                }
            } catch (SQLException e) {
                throw new IllegalStateException("Ошибка поиска пользователей", e);

            }
        });
        return results;
    }
}

