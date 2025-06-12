--liquibase formatted sql

--changeset taras.shevchuk:4


ALTER TABLE "Users" RENAME COLUMN "craeted_at" TO "created_at";
ALTER TABLE "Users" ALTER COLUMN "created_at" TYPE TIMESTAMP USING created_at AT TIME ZONE 'UTC';
ALTER TABLE "Users" ALTER COLUMN "created_at" SET DEFAULT NOW();

ALTER TABLE "Users" ALTER COLUMN "updated_at" TYPE TIMESTAMP USING updated_at AT TIME ZONE 'UTC';
ALTER TABLE "Users" ALTER COLUMN "updated_at" DROP NOT NULL;
ALTER TABLE "Users" ALTER COLUMN "updated_at" SET DEFAULT NULL;


ALTER TABLE "Businesses" RENAME COLUMN "craeted_at" TO "created_at";
ALTER TABLE "Businesses" ALTER COLUMN "created_at" TYPE TIMESTAMP USING created_at AT TIME ZONE 'UTC';
ALTER TABLE "Businesses" ALTER COLUMN "created_at" SET DEFAULT NOW();

ALTER TABLE "Businesses" ALTER COLUMN "updated_at" TYPE TIMESTAMP USING updated_at AT TIME ZONE 'UTC';
ALTER TABLE "Businesses" ALTER COLUMN "updated_at" DROP NOT NULL;
ALTER TABLE "Businesses" ALTER COLUMN "updated_at" SET DEFAULT NULL;

ALTER TABLE "Services" RENAME COLUMN "craeted_at" TO "created_at";
ALTER TABLE "Services" ALTER COLUMN "created_at" TYPE TIMESTAMP USING created_at AT TIME ZONE 'UTC';
ALTER TABLE "Services" ALTER COLUMN "created_at" SET DEFAULT NOW();

ALTER TABLE "Services" ALTER COLUMN "updated_at" TYPE TIMESTAMP USING updated_at AT TIME ZONE 'UTC';
ALTER TABLE "Services" ALTER COLUMN "updated_at" DROP NOT NULL;
ALTER TABLE "Services" ALTER COLUMN "updated_at" SET DEFAULT NULL;

ALTER TABLE "Bookings" ALTER COLUMN "date_time" TYPE TIMESTAMP USING date_time AT TIME ZONE 'UTC';
ALTER TABLE "Bookings" ALTER COLUMN "date_time" SET DEFAULT NOW();

ALTER TABLE "Bookings" RENAME COLUMN "craeted_at" TO "created_at";
ALTER TABLE "Bookings" ALTER COLUMN "created_at" TYPE TIMESTAMP USING created_at AT TIME ZONE 'UTC';
ALTER TABLE "Bookings" ALTER COLUMN "created_at" SET DEFAULT NOW();

ALTER TABLE "Bookings" ALTER COLUMN "updated_at" TYPE TIMESTAMP USING updated_at AT TIME ZONE 'UTC';
ALTER TABLE "Bookings" ALTER COLUMN "updated_at" DROP NOT NULL;
ALTER TABLE "Bookings" ALTER COLUMN "updated_at" SET DEFAULT NULL;

ALTER TABLE "Notification" RENAME COLUMN "craeted_at" TO "created_at";
ALTER TABLE "Notification" ALTER COLUMN "sent_at" TYPE TIMESTAMP USING sent_at AT TIME ZONE 'UTC';
ALTER TABLE "Notification" ALTER COLUMN "sent_at" SET DEFAULT NOW();

ALTER TABLE "Notification" ALTER COLUMN "created_at" TYPE TIMESTAMP USING created_at AT TIME ZONE 'UTC';
ALTER TABLE "Notification" ALTER COLUMN "created_at" SET DEFAULT NOW();

ALTER TABLE "Notification" ALTER COLUMN "updated_at" TYPE TIMESTAMP USING updated_at AT TIME ZONE 'UTC';
ALTER TABLE "Notification" ALTER COLUMN "updated_at" DROP NOT NULL;
ALTER TABLE "Notification" ALTER COLUMN "updated_at" SET DEFAULT NULL;
