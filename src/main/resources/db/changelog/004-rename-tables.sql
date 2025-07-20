--liquibase formatted sql

--changeset taras.shevchuk:4


ALTER TABLE "Businesses" RENAME TO businesses;

ALTER TABLE "Services" RENAME TO services;

ALTER TABLE "Bookings" RENAME TO bookings;

ALTER TABLE "Notifications" RENAME TO notifications;