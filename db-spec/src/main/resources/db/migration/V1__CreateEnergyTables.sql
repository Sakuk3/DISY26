-- Create dedicated schema and user for the API
DO
$$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'disy26_api') THEN
        CREATE ROLE disy26_api LOGIN PASSWORD 'disy26_api_pw';
    END IF;
END
$$;

CREATE SCHEMA IF NOT EXISTS api AUTHORIZATION disy26_api;
GRANT USAGE ON SCHEMA api TO disy26_api;
ALTER ROLE disy26_api SET search_path TO api;

GRANT CONNECT ON DATABASE disy26 TO disy26_api;

-- Create energy tables
CREATE TABLE associations
(
    id         BIGSERIAL PRIMARY KEY,
    code       TEXT        NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

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

CREATE TABLE hourly_percentages
(
    association_id       BIGINT        NOT NULL REFERENCES associations (id),
    hour_bucket          TIMESTAMPTZ   NOT NULL,
    self_consumption_pct NUMERIC(5, 2) NOT NULL CHECK (self_consumption_pct >= 0 AND self_consumption_pct <= 100),
    grid_dependency_pct  NUMERIC(5, 2) NOT NULL CHECK (grid_dependency_pct >= 0 AND grid_dependency_pct <= 100),
    created_at           TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at           TIMESTAMPTZ   NOT NULL DEFAULT now(),
    PRIMARY KEY (association_id, hour_bucket)
);
