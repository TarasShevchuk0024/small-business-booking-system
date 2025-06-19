--liquibase formatted sql

--changeset taras.shevchuk:5

-- Add column token for users

ALTER TABLE users ADD column token UUID;