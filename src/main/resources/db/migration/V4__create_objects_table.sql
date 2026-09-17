CREATE TABLE objects
(
    id                UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    bucket_id         UUID        NOT NULL REFERENCES buckets (id),
    added_by          UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    latest_version_id BIGINT,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE metadata
(
    id               BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    object_id        UUID        NOT NULL REFERENCES objects (id) ON DELETE CASCADE,
    etag             VARCHAR(64) NOT NULL,
    size_bytes       BIGINT      NOT NULL,
    content_type     VARCHAR(255),
    is_delete_marker BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now()
);

ALTER TABLE objects
    ADD CONSTRAINT fk_latest_version
        FOREIGN KEY (latest_version_id) REFERENCES metadata (id);