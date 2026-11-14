package tms.c42onl.socialnetworkproject.storage;

import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.model.Subscription;
import tms.c42onl.socialnetworkproject.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcSubscriptionStorage implements SubscriptionStorage{
    private final JDBCManager jdbcManager;
    private static final String INSERT_SQL = "INSERT INTO subscriptions (subscriber_id, author_id) VALUES (?, ?)";
    private static final String DELETE_SQL = "DELETE FROM subscriptions WHERE subscriber_id = ? AND author_id = ?";
    private static final String FIND_BY_USERS_SQL =
            "SELECT id, subscriber_id, author_id, created_at FROM subscriptions WHERE subscriber_id = ? AND author_id = ?";
    private static final String FIND_BY_ID_SQL =
            "SELECT id, subscriber_id, author_id, created_at FROM subscriptions WHERE id = ?";
    private static final String COUNT_SUBS_SQL = "SELECT COUNT(*) FROM subscriptions WHERE author_id = ?";
    private static final String FIND_SUBS_BY_AUTHOR_SQL = """
            SELECT u.id, u.login, u.first_name, u.last_name
            FROM users u
            JOIN subscriptions s ON u.id = s.subscriber_id
            WHERE s.author_id = ?
            ORDER BY u.first_name ASC, u.last_name ASC, u.id ASC
            """;
    private static final String FIND_AUTHOR_IDS_BY_SUBS_SQL = "SELECT author_id FROM subscriptions WHERE subscriber_id = ?";

    public JdbcSubscriptionStorage(JDBCManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }

    @Override
    public void save(Subscription subscription) {
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, subscription.getSubscriberId());
                statement.setLong(2, subscription.getAuthorId());
                statement.executeUpdate();
                try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        subscription.setId(generatedKeys.getLong(1));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void delete(Long subscriberId, Long authorId) {
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
                statement.setLong(1, subscriberId);
                statement.setLong(2, authorId);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Optional<Subscription> findByUsers(Long subscriberId, Long authorId) {
        final Subscription[] result = new Subscription[1];
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(FIND_BY_USERS_SQL)) {
                statement.setLong(1, subscriberId);
                statement.setLong(2, authorId);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()){
                        result[0] = mapRowToSubscriptions(resultSet);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return Optional.ofNullable(result[0]);
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        final Subscription[] result = new Subscription[1];
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                statement.setLong(1, id);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()){
                        result[0] = mapRowToSubscriptions(resultSet);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return Optional.ofNullable(result[0]);
    }

    @Override
    public int countSubscribers(Long authorId) {
        final int[] count = new int[1];
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(COUNT_SUBS_SQL)) {
                statement.setLong(1, authorId);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()){
                        count[0] = resultSet.getInt(1);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return count[0];
    }

    @Override
    public List<User> findSubscribersByAuthorId(Long authorId) {
        List<User> subscribers = new ArrayList<>();
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(FIND_SUBS_BY_AUTHOR_SQL)) {
                statement.setLong(1, authorId);
                try(ResultSet resultSet = statement.executeQuery()) {
                   while (resultSet.next()){
                       User user = new User();
                       user.setId(resultSet.getLong("id"));
                       user.setLogin(resultSet.getString("login"));
                       user.setFirstName(resultSet.getString("first_name"));
                       user.setLastName(resultSet.getString("last_name"));

                       subscribers.add(user);
                   }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return subscribers;
    }

    @Override
    public List<Long> findAuthorIdBySubscriberId(Long subscriberId) {
        List<Long> authorsIds = new ArrayList<>();
        jdbcManager.execute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(FIND_AUTHOR_IDS_BY_SUBS_SQL)) {
                statement.setLong(1, subscriberId);

                try(ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        authorsIds.add(resultSet.getLong("author_id"));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return authorsIds;
    }

    private Subscription mapRowToSubscriptions(ResultSet resultSet) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(resultSet.getLong("id"));
        subscription.setSubscriberId(resultSet.getLong("subscriber_id"));
        subscription.setAuthorId(resultSet.getLong("author_id"));
        if (resultSet.getTimestamp("created_at") != null) {
            subscription.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        }
        return subscription;
    }
}
