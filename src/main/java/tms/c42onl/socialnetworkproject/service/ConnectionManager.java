package tms.c42onl.socialnetworkproject.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Временный класс для получения соединения с бд
//TODO: Удалить
public class ConnectionManager {
    public static Connection getConnection() throws SQLException {
        String url = System.getProperty("DB_URL");
        String username = System.getProperty("DB_USERNAME");
        String password = System.getProperty("DB_PASSWORD");

        if (url == null || username == null || password == null) {
            throw new IllegalStateException("Переменные окружения DB_URL, DB_USERNAME и DB_PASSWORD не настроены!");
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, username, password);
    }
}