-- Verify flipr:airplane on pg

BEGIN;

    SELECT airplane_id
        FROM airplanes
    WHERE FALSE;

ROLLBACK;
