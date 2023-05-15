-- Database: oop

-- DROP DATABASE oop;

CREATE DATABASE oop
    WITH
    OWNER = oop
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA oop;

COMMENT ON DATABASE oop
    IS 'Database for Dijkstras Airport Project';
