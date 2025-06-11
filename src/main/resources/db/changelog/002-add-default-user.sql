--liquibase formatted sql

--changeset taras.shevchuk:2


INSERT INTO "Users" (
   "id",
   "first_name",
   "last_name",
   "email",
   "phone_number",
   "password",
   "type"
) VALUES (
             '880ec796-6633-4ee8-a57b-9e5b31fc6efe',
             'Anna',
             'Kovalchuk',
             'anna_kovalchuk1994@gmail.com',
             '+380503836457',
             'YTREWQ1234',
             'ADMIN'
);