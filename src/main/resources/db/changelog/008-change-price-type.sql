--liquibase formatted sql

--changeset taras.shevchuk:8

ALTER TABLE services
ALTER COLUMN price TYPE numeric(10,2) USING price::numeric;