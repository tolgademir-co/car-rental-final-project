-- ==========================
-- Rent-A-Car Database Schema
-- ==========================

DROP TABLE IF EXISTS rentals CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- USERS TABLE
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100)                                                    NOT NULL,
    email         VARCHAR(100) UNIQUE                                             NOT NULL,
    password_hash VARCHAR(256)                                                    NOT NULL,
    role          VARCHAR(20) CHECK (role IN ('ADMIN', 'CUSTOMER'))               NOT NULL,
    account_type  VARCHAR(20) CHECK (account_type IN ('INDIVIDUAL', 'CORPORATE')) NOT NULL,
    age           INT                                                             NOT NULL
);

-- VEHICLES TABLE
CREATE TABLE vehicles
(
    id            SERIAL PRIMARY KEY,
    type          VARCHAR(20) CHECK (type IN ('CAR', 'MOTORCYCLE', 'HELICOPTER')) NOT NULL,
    brand         VARCHAR(50)                                                     NOT NULL,
    model         VARCHAR(50)                                                     NOT NULL,
    value         NUMERIC(15, 2)                                                  NOT NULL,
    price_hourly  NUMERIC(10, 2),
    price_daily   NUMERIC(10, 2),
    price_weekly  NUMERIC(10, 2),
    price_monthly NUMERIC(10, 2)
);

-- RENTALS TABLE
CREATE TABLE rentals
(
    id         SERIAL PRIMARY KEY,
    user_id    INT       NOT NULL,
    vehicle_id INT       NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date   TIMESTAMP NOT NULL,
    deposit    DOUBLE PRECISION DEFAULT 0,
    status     VARCHAR(20)      DEFAULT 'ACTIVE',
    price      DOUBLE PRECISION DEFAULT 0,
    CONSTRAINT fk_rentals_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_rentals_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id) ON DELETE CASCADE
);