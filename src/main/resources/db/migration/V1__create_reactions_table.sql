CREATE TABLE reactions (
    -- Уникальный идентификатор реакции
    id bigserial PRIMARY KEY,
    -- Идентификатор пользователя, оставившего реакцию
    user_id bigint NOT NULL,
    -- Идентификатор поста, к которому оставлена реакция
    post_id bigint NOT NULL,
    -- Тип реакции (строка до 10 символов, только LIKE или DISLIKE)
    reaction_type varchar(10) NOT NULL,
    -- Дата и время создания реакции (текущее время системы)
    created_at timestamp DEFAULT current_timestamp,

    -- Внешний ключ с таблицей пользователя (если пользователя удалят, его лайки тоже удалятся)
    CONSTRAINT fk_reactions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

    -- Внешний ключ с таблицей постов (если пост удалят, лайки к нему тоже удалятся)
    CONSTRAINT fk_reactions_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,

    -- Уникальный индекс: один пользователь - одна реакция
    CONSTRAINT uq_user_post_reaction UNIQUE (user_id, post_id),

    -- Ограничение ввода: только LIKE и DISLIKE
    CONSTRAINT chk_reaction_type CHECK (reaction_type IN ('LIKE', 'DISLIKE'))
);