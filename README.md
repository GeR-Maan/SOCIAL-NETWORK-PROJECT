## Что сделано
- Миграция V2__create_posts.sql: таблица posts (id, author_id → users, text, created_at)
- Модель Post, PostStorage + JdbcPostStorage (INSERT ... RETURNING id)
- PostService: проверки (есть текст или фото, фото не больше 5), сохранение поста и фото в одной транзакции через JDBCManager
- CreatePostServlet (/posts/create) и create-post.jsp

## Временно
- Автор поста — заглушка "1". Заменю на текущего юзера после слияния сессии пользователя и контроля доступа
- Фото сохраняет StubPostPhotoService. Подставлю класс от TMS-17, когда он будет готов

## Как проверить
1. Настроить переменные окружения Tomcat (DB_URL, DB_USER, DB_PASSWORD).
2. Запустить сервер Tomcat.
2. Открыть /posts/create
3. Пустая форма → ошибка; текст → пост в таблице posts; 6 фото → ошибка
