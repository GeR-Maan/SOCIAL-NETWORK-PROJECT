CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    subscriber_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT fk_subscriptions_subscriber
    FOREIGN KEY (subscriber_id) REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT fk_subscriptions_author
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT uk_subscriber_author
    UNIQUE (subscriber_id, author_id),

    CONSTRAINT chk_self_subscription
    CHECK (subscriber_id <> author_id)
);

CREATE INDEX idx_subscriptions_subscriber ON subscriptions(subscriber_id);
CREATE INDEX idx_subscriptions_author ON subscriptions(author_id);