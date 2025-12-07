CREATE TABLE IF NOT EXISTS fridge (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_on TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    user_account_id BIGINT NOT NULL,

    CONSTRAINT fk_fridge_user
        FOREIGN KEY (user_account_id)
            REFERENCES user_account(id)
            ON DELETE CASCADE,

    CONSTRAINT uq_fridge_user_name
        UNIQUE (user_account_id, name)
);

CREATE INDEX IF NOT EXISTS idx_fridge_user_account_id
    ON fridge(user_account_id);