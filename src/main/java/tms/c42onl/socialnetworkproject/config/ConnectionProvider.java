package tms.c42onl.socialnetworkproject.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private final DatabaseConfig databaseConfig;

    public ConnectionProvider(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    databaseConfig.getUrl(),
                    databaseConfig.getUsername(),
                    databaseConfig.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Something wrong");
        }
    }
}
