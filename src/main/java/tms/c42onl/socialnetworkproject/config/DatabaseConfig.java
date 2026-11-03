package tms.c42onl.socialnetworkproject.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс конфигурации подключения к базе данных PostgreSQL.
 *
 * <p></p>Параметры подключения берутся из переменных окружения:
 * <ul>
 *     <li>DB_URL — адрес подключения к базе данных</li>
 *     <li>DB_USER — имя пользователя PostgreSQL</li>
 *     <li>DB_PASSWORD — пароль пользователя PostgreSQL</li>
 * </ul>
 *
 * <p>Переменные окружения необходимо указать
 * в конфигурации запуска приложения.
 *
 * <p>В IntelliJ IDEA:
 * Run → Edit Configurations → Tomcat Server.
 *
 * <p>Пример значений:
 * <pre>
 * DB_URL=jdbc:postgresql://localhost:5432/postgres
 * DB_USER=postgres
 * DB_PASSWORD=ваш_пароль
 * </pre>
 *
 * @implNote Класс не устанавливает соединение с БД,
 * а только хранит параметры для подключения.
 */

@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * Создаёт объект конфигурации подключения к базе данных.
     *
     * <p>Метод считывает три обязательные переменные
     * окружения: DB_URL, DB_USER и DB_PASSWORD.
     *
     * @return объект DatabaseConfig с параметрами подключения
     * @throws IllegalArgumentException если хотя бы одна
     * переменная окружения отсутствует или пуста
     */
    public static DatabaseConfig databaseConfig() {
        return new DatabaseConfig(
                requiredEnv("DB_URL"),
                requiredEnv("DB_USER"),
                requiredEnv("DB_PASSWORD")
        );
    }

    /**
     * Получает значение переменной окружения по её названию.
     *
     * <p>Для получения используется System.getenv(name).
     * Если переменная отсутствует или содержит пустое значение,
     * выбрасывается исключение.
     *
     * @param name название переменной окружения
     * @return значение переменной окружения
     * @throws IllegalArgumentException если переменная
     * отсутствует или содержит пустую строку
     */
    public static String requiredEnv(String name) {
        String value = System.getenv(name);
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Не задана переменная окружения %s".formatted(value));
        }
        return value;
    }
}
