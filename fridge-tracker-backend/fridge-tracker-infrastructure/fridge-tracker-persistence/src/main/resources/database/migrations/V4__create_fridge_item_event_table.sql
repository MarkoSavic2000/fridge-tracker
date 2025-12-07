CREATE TABLE IF NOT EXISTS fridge_item_event
(
    id             BIGSERIAL PRIMARY KEY,
    fridge_item_id BIGINT       NOT NULL,
    fridge_id      BIGINT       NOT NULL,
    keycloak_id    VARCHAR(255) NOT NULL,
    type           VARCHAR(50)  NOT NULL,
    quantity       NUMERIC(10, 2),
    created_on     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_fridge_item_event_fridge
        FOREIGN KEY (fridge_id) REFERENCES fridge (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_fridge_item_event_fridge_item
        FOREIGN KEY (fridge_item_id) REFERENCES fridge_item (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_fridge_item_event_user_account
        FOREIGN KEY (keycloak_id) REFERENCES user_account (keycloak_id)
            ON DELETE CASCADE
);