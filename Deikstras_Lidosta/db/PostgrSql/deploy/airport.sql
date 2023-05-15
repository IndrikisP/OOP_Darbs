-- Deploy flipr:airport to pg

BEGIN;

    CREATE TABLE oop.airports (
         airport_id          UUID        PRIMARY KEY,
         code                TEXT,
         city_name           TEXT
    );

COMMIT;
