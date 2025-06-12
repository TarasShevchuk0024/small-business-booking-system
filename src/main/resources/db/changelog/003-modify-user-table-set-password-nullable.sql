--liquibase formatted sql

--changeset taras.shevchuk:3


ALTER TABLE "Users" ALTER column "password" DROP NOT NULL;