create table posts
(
    id         bigserial
        constraint posts_pk
            primary key,
    author_id  bigint    not null
        constraint posts_users_id_fk
            references users (id),
    text       varchar,
    created_at timestamp not null default CURRENT_TIMESTAMP
);