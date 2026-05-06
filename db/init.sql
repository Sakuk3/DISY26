-- Create dedicated schema and user for the API
DO $$
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

