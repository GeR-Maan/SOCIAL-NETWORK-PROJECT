CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          post_id BIGINT NOT NULL,
                          author_id BIGINT NOT NULL,
                          text TEXT NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_comments_post
                              FOREIGN KEY (post_id)
                                  REFERENCES posts(id),

                          CONSTRAINT fk_comments_author
                              FOREIGN KEY (author_id)
                                  REFERENCES users(id),

                          CONSTRAINT chk_comments_text
                              CHECK (LENGTH(TRIM(text)) > 0)
);

CREATE INDEX idx_comments_post_created
    ON comments(post_id, created_at, id);