-- Verify flipr:flight on pg

BEGIN;

    SELECT flight_id
        FROM flights
    WHERE FALSE;

ROLLBACK;
