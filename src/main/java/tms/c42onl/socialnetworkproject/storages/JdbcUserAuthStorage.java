package tms.c42onl.socialnetworkproject.storages;


import tms.c42onl.socialnetworkproject.models.User;

import java.sql.*;
import java.util.Optional;


public class JdbcUserAuthStorage implements UserAuthStorage {
    private final String SQL = "SELECT id, login, password, first_name, last_name, birth_date FROM users WHERE login = ?";

    // private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/social_network_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅PostgreSQL успешно подключен JdbcUserAuthStorage");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Ошибка: Не удалось подключить PostgreSQL");
            e.printStackTrace();
        }
    }

//    private final JDBCManager jdbcManager;

//    public JdbcUserAuthStorage(JDBCManager jdbcManager) {
//        this.jdbcManager = jdbcManager;
//    }

    // TODO: Вернуться и обсудить  использование JDBCManager.
    // Сейчас лямбда и массив-коробочку, чтобы не хардкодить пароли.
    //
    @Override
    public Optional<User> findByLogin(String login) {
        if (login == null || login.trim().isBlank()) {
            return Optional.empty();
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getLong("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        //если у пользователя в базе бд др == null
                        rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null)
                        //rs.getDate("birth_date").toLocalDate())
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}