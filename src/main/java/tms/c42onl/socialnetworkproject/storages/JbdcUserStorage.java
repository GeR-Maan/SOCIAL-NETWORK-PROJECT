package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class JbdcUserStorage implements UserStorage{

    private static final String FIND_BY_LOGIN_SQL = "SELECT id, login, password, first_name, last_name, birth_date FROM users WHERE login =?";
    private static final String FIND_BY_ID_SQL = "SELECT id, login, password, first_name, last_name, birth_date FROM users WHERE id =?";
    private static final String INSERT_USER_SQL = "INSERT INTO users (login, password, first_name, last_name, birth_date) VALUES (?, ?, ?, ?, ?)";
    private final JDBCManager jdbcManager;

    public JbdcUserStorage(JDBCManager jdbcManager){
        this.jdbcManager = jdbcManager;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        User[] foundUser = new User[1];
        jdbcManager.execute(connection -> {
        try(PreparedStatement statement = connection.prepareStatement(FIND_BY_LOGIN_SQL)) {
                statement.setString(1, login);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        foundUser[0] = new User(
                                resultSet.getLong("id"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getObject("birth_date", LocalDate.class)
                        );
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return  Optional.ofNullable(foundUser[0]);
    }

    @Override
    public Optional<User> findById(Long id) {
        User[] foundUser = new User[1];
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                statement.setLong(1, id);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        foundUser[0] = new User(
                                resultSet.getLong("id"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getObject("birth_date", LocalDate.class)
                        );
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return  Optional.ofNullable(foundUser[0]);
    }

    @Override
    public void save(User user) {
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getFirstName());
                statement.setString(4, user.getLastName());
                statement.setObject(5, user.getBirthDate());
                statement.executeUpdate();

                try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
