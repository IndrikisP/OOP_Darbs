-- Deploy flipr:flight to pg

BEGIN;

    CREATE TABLE oop.flights (
         flight_id          UUID        PRIMARY KEY,
         from_id            UUID,
         to_id              UUID,
         distance           BIGINT,
         price              NUMERIC(10, 2),
         time_of_arrival    TIMESTAMPTZ NOT NULL,
         time_of_departure  TIMESTAMPTZ NOT NULL,
         timezone           TEXT,
         airplane_id        UUID,
         company            TEXT,
         CONSTRAINT from_id FOREIGN KEY (from_id) REFERENCES oop.airports(airport_id) ON UPDATE CASCADE ON DELETE RESTRICT,
         CONSTRAINT to_id FOREIGN KEY (to_id) REFERENCES oop.airports(airport_id) ON UPDATE CASCADE ON DELETE RESTRICT,
         CONSTRAINT airplane_id FOREIGN KEY (airplane_id) REFERENCES oop.airplanes(airplane_id) ON UPDATE CASCADE ON DELETE RESTRICT
    );

COMMIT;
