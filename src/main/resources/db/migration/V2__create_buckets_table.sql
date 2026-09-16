CREATE TABLE buckets
(
    id                UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    name              TEXT             NOT NULL UNIQUE,
    created_by        UUID             NOT NULL,
    object_versioning BOOLEAN          NOT NULL DEFAULT TRUE,
    created_at        TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_bucket_created_by FOREIGN KEY (created_by)
        REFERENCES users (id)
        ON DELETE CASCADE
);
