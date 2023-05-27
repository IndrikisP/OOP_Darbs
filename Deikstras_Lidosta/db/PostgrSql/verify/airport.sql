-- Verify flipr:airport on pg

BEGIN;

    SELECT airport_id
        FROM airports
    WHERE FALSE;

ROLLBACK;
