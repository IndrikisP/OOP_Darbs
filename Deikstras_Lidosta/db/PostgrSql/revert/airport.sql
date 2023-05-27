-- Revert flipr:airport from pg

BEGIN;

    DROP TABLE oop.airports;

COMMIT;
