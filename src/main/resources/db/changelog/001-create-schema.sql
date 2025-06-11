--liquibase formatted sql

--changeset taras.shevchuk:1


CREATE TABLE "Users" (
        "id" UUID PRIMARY KEY,
        "first_name" VARCHAR(50) NOT NULL,
        "last_name" VARCHAR(50) NOT NULL,
        "email" VARCHAR(245) NOT NULL UNIQUE,
        "phone_number" VARCHAR(16) NOT NULL UNIQUE,
        "password" VARCHAR(255) NOT NULL,
        "type" VARCHAR(10) NOT NULL,
        "craeted_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "updated_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC')
);

CREATE TABLE "Businesses" (
        "id" UUID PRIMARY KEY,
        "business_name" VARCHAR(50) NOT NULL,
        "description" TEXT NOT NULL,
        "craeted_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "updated_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "user_id" UUID REFERENCES "Users"("id")
);

CREATE TABLE "Services" (
        "id" UUID PRIMARY KEY,
        "service_name" VARCHAR(50) NOT NULL,
        "duration" INT NOT NULL,
        "price" MONEY NOT NULL,
        "craeted_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "updated_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "business_id" UUID REFERENCES "Businesses"("id")
);

CREATE TABLE "Bookings" (
        "id" UUID PRIMARY KEY,
        "date_time" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "status" VARCHAR(50) NOT NULL,
        "craeted_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "updated_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "service_id" UUID REFERENCES "Services"("id"),
        "user_id" UUID REFERENCES "Users"("id")
);

CREATE TABLE "Notification" (
        "id" UUID PRIMARY KEY,
        "message" TEXT NOT NULL,
        "sent_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "type" VARCHAR(10) NOT NULL,
        "craeted_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "updated_at" TIMESTAMPTZ NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        "user_id" UUID REFERENCES "Users"("id"),
        "booking_id" UUID REFERENCES "Bookings"("id")
);