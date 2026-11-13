package tms.c42onl.socialnetworkproject.execptions.execptionTranslator;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresSqlExceptionTranslator implements SqlExceptionTranslator {

    private static final Logger log = LoggerFactory.getLogger(PostgresSqlExceptionTranslator.class);

    @Override
    public RuntimeException translate(String operation, SQLException e) {
        log.error("SQL error during '{}' [SQLState={}]", operation, e.getSQLState(), e);

        String sqlState = e.getSQLState();
        if (sqlState == null || sqlState.length() < 2) {
            return new StorageException("SQL error during '" + operation + "'", e);
        }

        return switch (sqlState.substring(0, 2)) {
            case "08" -> new StorageUnavailableException(
                    "Connection failure during '" + operation + "'", e);
            case "23" -> new DataIntegrityException(
                    "Constraint violation during '" + operation + "'", e);
            case "40", "57" -> new StorageTimeoutException(
                    "Lock/timeout during '" + operation + "'", e);
            default -> new StorageException(
                    "SQL error during '" + operation + "'", e);
        };
    }
}
