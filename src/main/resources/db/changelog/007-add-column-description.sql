--liquibase formatted sql

--changeset taras.shevchuk:7

ALTER TABLE services ADD column description TEXT;