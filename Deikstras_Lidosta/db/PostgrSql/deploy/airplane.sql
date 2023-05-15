-- Deploy flipr:airplane to pg

BEGIN;

    CREATE TABLE oop.airplanes (
         airplane_id          UUID        PRIMARY KEY,
         type                 TEXT,
         passenger_count      TEXT
    );


COMMIT;
