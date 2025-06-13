--liquibase formatted sql

--changeset taras.shevchuk:2


ALTER TABLE "Users" RENAME TO users;