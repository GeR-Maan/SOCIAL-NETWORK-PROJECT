package tms.c42onl.socialnetworkproject.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCManager  {

    private final ConnectionProvider provider;

    public JDBCManager(ConnectionProvider provider) {
       this.provider = provider;
    }

    @FunctionalInterface
    public interface PostgresWork {
        public void execute(Connection connection);
    }

    public void execute(PostgresWork postgresWork) {
        try (Connection connection = provider.getConnection()) {
            connection.setAutoCommit(false);
            try {
                postgresWork.execute(connection);
                connection.commit();
            } catch (SQLException | RuntimeException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
