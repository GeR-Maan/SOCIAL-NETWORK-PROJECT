package tms.c42onl.socialnetworkproject.execptions.execptionTranslator;

import java.sql.SQLException;

public interface SqlExceptionTranslator {
    RuntimeException translate(String operation, SQLException e);
}
