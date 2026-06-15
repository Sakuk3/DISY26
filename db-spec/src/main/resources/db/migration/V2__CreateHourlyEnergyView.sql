CREATE OR REPLACE VIEW v_hourly_energy AS
SELECT
    a.code AS association,
    u.hour_bucket AS hour,
    u.community_produced_kwh,
    u.community_used_kwh,
    u.grid_used_kwh,
    COALESCE(p.self_consumption_pct, 0.00) AS self_consumption_pct,
    COALESCE(p.grid_dependency_pct, 0.00) AS grid_dependency_pct
FROM hourly_usage u
    JOIN associations a
ON a.id = u.association_id
    LEFT JOIN hourly_percentages p
    ON p.association_id = u.association_id
    AND p.hour_bucket = u.hour_bucket;