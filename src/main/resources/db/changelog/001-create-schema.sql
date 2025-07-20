--liquibase formatted sql

--changeset taras.shevchuk:1


CREATE TABLE "Users" (
        "id" UUID PRIMARY KEY,
        "first_name" VARCHAR(50) NOT NULL,
        "last_name" VARCHAR(50) NOT NULL,
        "email" VARCHAR(245) NOT NULL UNIQUE,
        "phone_number" VARCHAR(16) NOT NULL UNIQUE,
        "password" VARCHAR(255),
        "type" VARCHAR(10) NOT NULL,
        "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
        "updated_at" TIMESTAMP NULL
);

CREATE TABLE "Businesses" (
        "id" UUID PRIMARY KEY,
        "business_name" VARCHAR(50) NOT NULL,
        "description" TEXT NOT NULL,
        "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
        "updated_at" TIMESTAMP NULL,
        "user_id" UUID REFERENCES "Users"("id")
);

CREATE TABLE "Services" (
        "id" UUID PRIMARY KEY,
        "service_name" VARCHAR(50) NOT NULL,
        "duration" INT NOT NULL,
        "price" MONEY NOT NULL,
        "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
        "updated_at" TIMESTAMP NULL,
        "business_id" UUID REFERENCES "Businesses"("id")
);

CREATE TABLE "Bookings" (
        "id" UUID PRIMARY KEY,
        "date_time" TIMESTAMP NOT NULL DEFAULT NOW(),
        "status" VARCHAR(50) NOT NULL,
        "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
        "updated_at" TIMESTAMP NULL,
        "service_id" UUID REFERENCES "Services"("id"),
        "user_id" UUID REFERENCES "Users"("id")
);

CREATE TABLE "Notifications" (
        "id" UUID PRIMARY KEY,
        "message" TEXT NOT NULL,
        "sent_at" TIMESTAMP NOT NULL DEFAULT NOW(),
        "type" VARCHAR(10) NOT NULL,
        "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
        "updated_at" TIMESTAMP NULL,
        "user_id" UUID REFERENCES "Users"("id"),
        "booking_id" UUID REFERENCES "Bookings"("id")
);