package tms.c42onl.socialnetworkproject.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DatabaseConfig {

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public static DatabaseConfig databaseConfig() {
        return new DatabaseConfig(
                requiredEnv("DB_URL"),
                requiredEnv("DB_USER"),
                requiredEnv("DB_PASSWORD")
        );
    }

    public static String requiredEnv(String name) {
        String value = System.getenv(name);
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Не задана переменная окружения %s".formatted(value));
        }
        return value;
    }
}
