package tms.c42onl.socialnetworkproject.storages;


import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.User;

import java.sql.*;
import java.util.Optional;


public class JdbcUserAuthStorage implements UserAuthStorage {
    private final String SQL = "SELECT id, login, password, first_name, last_name, birth_date FROM users WHERE login = ?";

    private final JDBCManager jdbcManager;

    public JdbcUserAuthStorage(JDBCManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }

    // TODO: Вернуться и обсудить  использование JDBCManager.
    // Сейчас лямбда и массив-коробочку, чтобы не хардкодить пароли.
    //
    @Override
    public Optional<User> findByLogin(String login) {
        if (login == null || login.trim().isBlank()) {
            return Optional.empty();
        }

        return jdbcManager.executeWithResult(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(SQL)) {
                ps.setString(1, login);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(new User(
                                rs.getLong("id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null
                        ));
                    }
                }
            }
            return Optional.empty();
        });
    }
}