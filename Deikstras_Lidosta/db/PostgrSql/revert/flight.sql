-- Revert flipr:flight from pg

BEGIN;

    DROP TABLE oop.flights;

COMMIT;
