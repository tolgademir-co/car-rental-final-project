-- ========================
-- Rent-A-Car Seed Data
-- ========================

-- Clean tables before inserting
TRUNCATE TABLE rentals RESTART IDENTITY CASCADE;
TRUNCATE TABLE vehicles RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;

-- USERS
INSERT INTO users (name, email, password_hash, role, account_type, age)
VALUES
    ('Admin User', 'admin@test.com',
     '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', -- password: admin123 (SHA-256)
     'ADMIN', 'INDIVIDUAL', 35),
    ('Corporate User', 'corp@test.com',
     '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', -- password: 12345
     'CUSTOMER', 'CORPORATE', 40),
    ('Individual User', 'user@test.com',
     '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', -- password: 12345
     'CUSTOMER', 'INDIVIDUAL', 28);


-- VEHICLES
INSERT INTO vehicles (type, brand, model, value, price_hourly, price_daily, price_weekly, price_monthly)
VALUES
    ('CAR', 'BMW', '320i', 1500000, 300, 1500, 6000, 20000),
    ('CAR', 'Mercedes', 'E200', 2500000, 500, 2500, 10000, 35000),
    ('CAR', 'Honda', 'Civic', 900000, 200, 1000, 4000, 12000),
    ('MOTORCYCLE', 'Yamaha', 'R6', 450000, 100, 500, 2000, 6000),
    ('HELICOPTER', 'Bell', '407', 8000000, 2000, 10000, 40000, 150000),
    ('CAR', 'Fiat', 'Egea', 600000, 150, 750, 3000, 9000);