CREATE TABLE associations
(
    id         BIGSERIAL PRIMARY KEY,
    code       TEXT        NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE energy_events
(
    id             BIGSERIAL PRIMARY KEY,
    association_id BIGINT           NOT NULL REFERENCES associations (id),
    event_type     TEXT             NOT NULL,
    kwh            NUMERIC(12, 3)   NOT NULL CHECK (kwh >= 0),
    occurred_at    TIMESTAMPTZ      NOT NULL,
    created_at     TIMESTAMPTZ      NOT NULL DEFAULT now()
);
CREATE INDEX idx_energy_events_occurred_at ON energy_events (occurred_at);
CREATE INDEX idx_energy_events_association ON energy_events (association_id);

CREATE TABLE hourly_usage
(
    association_id         BIGINT         NOT NULL REFERENCES associations (id),
    hour_bucket            TIMESTAMPTZ    NOT NULL,
    community_produced_kwh NUMERIC(12, 3) NOT NULL DEFAULT 0 CHECK (community_produced_kwh >= 0),
    community_used_kwh     NUMERIC(12, 3) NOT NULL DEFAULT 0 CHECK (community_used_kwh >= 0),
    grid_used_kwh          NUMERIC(12, 3) NOT NULL DEFAULT 0 CHECK (grid_used_kwh >= 0),
    created_at             TIMESTAMPTZ    NOT NULL DEFAULT now(),
    updated_at             TIMESTAMPTZ    NOT NULL DEFAULT now(),
    PRIMARY KEY (association_id, hour_bucket),
    CHECK (community_used_kwh <= community_produced_kwh)
);
CREATE INDEX idx_hourly_usage_hour ON hourly_usage (hour_bucket);

CREATE TABLE hourly_percentages
(
    association_id       BIGINT      NOT NULL REFERENCES associations (id),
    hour_bucket          TIMESTAMPTZ NOT NULL,
    self_consumption_pct NUMERIC(5, 2) NOT NULL CHECK (self_consumption_pct >= 0 AND self_consumption_pct <= 100),
    grid_dependency_pct  NUMERIC(5, 2) NOT NULL CHECK (grid_dependency_pct >= 0 AND grid_dependency_pct <= 100),
    created_at           TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at           TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (association_id, hour_bucket)
);
CREATE INDEX idx_hourly_percentages_hour ON hourly_percentages (hour_bucket);

CREATE VIEW v_hourly_energy AS
SELECT a.code AS association,
       u.hour_bucket AS hour,
  u.community_produced_kwh,
  u.community_used_kwh,
  u.grid_used_kwh,
  p.self_consumption_pct,
  p.grid_dependency_pct
FROM
  hourly_usage u
  JOIN associations a ON a.id = u.association_id
  LEFT JOIN hourly_percentages p ON p.association_id = u.association_id
  AND p.hour_bucket = u.hour_bucket;

INSERT INTO associations (code)
VALUES ('COMMUNITY');
