--liquibase formatted sql
--changeset Osipov Konstantin:first_tables
CREATE TABLE IF NOT EXISTS credit
(
    credit_id         UUID PRIMARY KEY,
    amount            DECIMAL,
    term              INT,
    monthly_payment   DECIMAL,
    rate              DECIMAL,
    psk               DECIMAL,
    payment_schedule  JSONB,
    insurance_enabled BOOLEAN,
    salary_client     BOOLEAN,
    credit_status     VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS passport
(
    passport_id   UUID PRIMARY KEY,
    passport_data JSONB
);

create table employment
(
    employment_id   UUID PRIMARY KEY,
    employment_data JSONB
);
CREATE TABLE IF NOT EXISTS client
(
    client_id        UUID PRIMARY KEY,
    first_name       VARCHAR,
    last_name        VARCHAR,
    middle_name      VARCHAR,
    birth_date       DATE,
    email            VARCHAR,
    gender           VARCHAR,
    marital_status   VARCHAR,
    dependent_amount INT,
    passport_id      UUID REFERENCES passport (passport_id),
    employment_id    UUID REFERENCES employment (employment_id),
    account_number   VARCHAR
);

CREATE TABLE IF NOT EXISTS statement
(
    statement_id   UUID PRIMARY KEY,
    client_id      UUID REFERENCES client (client_id),
    credit_id      UUID REFERENCES credit (credit_id),
    status         VARCHAR,
    creation_date  TIMESTAMP,
    applied_offer  JSONB,
    sign_date      TIMESTAMP,
    ses_code       VARCHAR,
    status_history JSONB
);