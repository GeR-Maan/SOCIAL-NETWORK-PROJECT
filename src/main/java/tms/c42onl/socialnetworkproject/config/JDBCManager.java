package tms.c42onl.socialnetworkproject.config;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCManager  {

    private final ConnectionProvider provider;

    public JDBCManager(ConnectionProvider provider) {
       this.provider = provider;
    }

    public void execute() {
        try (Connection connection = provider.getConnection()) {
            connection.setAutoCommit(false);
            try {


                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
