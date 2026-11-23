CREATE TABLE post_images (
     id         BIGSERIAL PRIMARY KEY,
     post_id    BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
     file_path  VARCHAR(500) NOT NULL,
     sort_order INT NOT NULL,

     CONSTRAINT uq_post_sort UNIQUE (post_id, sort_order),
     CONSTRAINT chk_sort_order CHECK (sort_order BETWEEN 1 AND 5)
);