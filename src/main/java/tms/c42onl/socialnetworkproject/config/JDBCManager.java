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

    @FunctionalInterface
    public interface PostgresFunction<T> {
        T apply(Connection connection) throws SQLException;
    }

    public void execute(PostgresWork postgresWork) {
        try (Connection connection = provider.getConnection()) {
            connection.setAutoCommit(false);
            try {
                postgresWork.execute(connection);
                connection.commit();
                System.out.println("commit successful");
            } catch (SQLException | RuntimeException e) {
                try {
                    connection.rollback();
                    System.out.println("rollback successful");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T executeWithResult(PostgresFunction<T> postgresFunction) {
        try (Connection connection = provider.getConnection()) {
            connection.setAutoCommit(false);
            try {
                // Выполняем работу и сохраняем результат
                T result = postgresFunction.apply(connection);

                connection.commit();
                System.out.println("commit successful");

                // Возвращаем полученный результат наружу
                return result;
            } catch (SQLException | RuntimeException e) {
                try {
                    connection.rollback();
                    System.out.println("rollback successful");
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
