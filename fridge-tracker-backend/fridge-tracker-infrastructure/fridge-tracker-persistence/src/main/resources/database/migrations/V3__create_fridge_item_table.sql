CREATE TABLE IF NOT EXISTS fridge_item
(
    id               BIGSERIAL PRIMARY KEY,
    fridge_id        BIGINT         NOT NULL,
    name             VARCHAR(255)   NOT NULL,
    quantity         NUMERIC(10, 2) NOT NULL,
    measurement_unit VARCHAR(50)    NOT NULL,
    category         VARCHAR(50)    NOT NULL,
    stored_on        TIMESTAMPTZ    NOT NULL,
    expires_on       DATE           NOT NULL,
    note             TEXT,
    created_on       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    modified_on      TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    deleted          BOOLEAN        NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_fridge_item_fridge
        FOREIGN KEY (fridge_id) REFERENCES fridge (id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_fridge_item_fridge_id
    ON fridge_item(fridge_id);