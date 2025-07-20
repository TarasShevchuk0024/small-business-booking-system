--liquibase formatted sql

--changeset taras.shevchuk:6


INSERT INTO users (
   id,
   first_name,
   last_name,
   email,
   phone_number,
   password,
   type,
   status
) VALUES (
             '880ec796-6633-4ee8-a57b-9e5b31fc6efe',
             'Roman',
             'Sad',
             'roma_sad@gmail.com',
             '+380634569856',
             'YTREWQ1234',
             'ROLE_USER',
             'ACTIVE'
);