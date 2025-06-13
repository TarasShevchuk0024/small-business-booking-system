--liquibase formatted sql

--changeset taras.shevchuk:3


ALTER TABLE users ADD COLUMN status VARCHAR(16);
