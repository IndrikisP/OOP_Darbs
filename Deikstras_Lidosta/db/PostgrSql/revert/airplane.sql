-- Revert flipr:airplane from pg

BEGIN;

    DROP TABLE oop.airplanes;

COMMIT;
