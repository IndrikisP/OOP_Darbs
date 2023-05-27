-- Deploy flipr:airplane to pg

BEGIN;

    CREATE TABLE oop.airplanes (
         airplane_id          UUID        PRIMARY KEY,
         type                 TEXT,
         model                TEXT,
         passenger_count      INT
    );


COMMIT;
