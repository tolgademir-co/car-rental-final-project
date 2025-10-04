# Rent-A-Car System

A console-based car rental system built with **Java 21**, **PostgreSQL**, and **JDBC**.  
This project was developed as a final project to demonstrate layered architecture, business rules, and database integration.

---

## 🚀 Technologies
- Java 21
- PostgreSQL 16+
- JDBC
- Maven

---

## 📂 Project Structure

src/main/java/com/tolgademir/rentacar

├── model # Entity classes (Car, Helicopter, Motorcycle, Rental, User, Vehicle)

├── dao # Data Access Objects (RentalDao, UserDao, VehicleDao)

├── service # Business logic (RentalService, UserService, VehicleService)

├── util # Utilities (DBConnection, HashUtil)

└── ui # Console menus (AdminMenu, CustomerMenu, Main)

---

## 👤 User Roles
- **ADMIN**
    - Add / List Vehicle (with pagination)
    - Update Vehicle
    - Delete Vehicle
    - View All Rentals (detailed with JOIN)
    - Logout

- **CUSTOMER**
    - List Vehicles (with pagination & filtering by type, brand, price range)
    - Rent Vehicles
    - My Rentals (detailed with JOIN)
    - Cancel / Complete Rentals
    - Logout

---

## 📌 Business Rules
- **Login/Register** → Email + password (hashed with SHA-256).
- **Corporate accounts** must rent for **at least 30 days**.
- If **vehicle value > 2,000,000 TL**:
    - Customer must be **≥ 30 years old**.
    - A **10% deposit** is required.
- Date overlap check → A vehicle cannot be rented in overlapping periods.
- Deposit lifecycle:
    - On rent → deposit is reserved.
    - On cancel → deposit refunded.
    - On complete → deposit refunded (or adjusted).
- **Dynamic pricing**:
    - Hourly, Daily, Weekly, Monthly price calculation depending on rental duration.
- **Transactions (commit/rollback)**:
    - Rental creation, cancellation, and completion are handled inside transaction blocks to ensure data consistency.
- **JOIN Queries**:
    - Rentals are listed with user and vehicle details for both customers and admins.
- **Filtering & Pagination**:
  - Vehicles can be listed with pagination and filtered by type, brand, or price range.

---

## 🗄️ Database Schema
**users**
- id (PK)
- name
- email (unique, not null)
- password_hash
- role (ADMIN/CUSTOMER)
- account_type (INDIVIDUAL/CORPORATE)
- age

**vehicles**
- id (PK)
- type (CAR/MOTORCYCLE/HELICOPTER)
- brand
- model
- value
- price_hourly / daily / weekly / monthly

**rentals**
- id (PK)
- user_id (FK → users.id)
- vehicle_id (FK → vehicles.id)
- start_date / end_date
- deposit
- status (ACTIVE/CANCELLED/COMPLETED)
- price

---

## 🌱 Seed Data
Example initial data for testing:

### Users
- **Admin** → `admin@test.com / admin123`
- **Corporate** → `corp@test.com / 12345`
- **Individual** → `user@test.com / 12345`

### Vehicles
- BMW 320i → 1.500.000 TL
- Mercedes E200 → 2.500.000 TL
- Honda Civic → 900.000 TL
- Yamaha R6 → 450.000 TL
- Bell 407 (Helicopter) → 8.000.000 TL
- Fiat Egea → 600.000 TL

---

## ▶️ Run Instructions

1. Create PostgreSQL database:
   ```sql
   CREATE DATABASE rentacar;

   Run schema.sql to create tables.

2. Run **schema.sql** to create tables.

3. Run **seed_data.sql** to insert sample users and vehicles.

4. Start the application in IntelliJ:

   └── **Main.java**

5. Login with seed users and test the system.

---

## 📖 Example Usage

=== RENT-A-CAR SYSTEM ===
1. Register
2. Login
0. Exit

*Select: 2
Email: corp@test.com
Password: 12345
✅ Login successful: corp@test.com

=== CUSTOMER MENU ===
1. List Vehicles (Paged)
2. Rent Vehicle
3. My Rentals (Detailed)
4. Cancel Rental
5. Complete Rental
0. Logout

---

## 📜 License

This project is licensed under the **MIT License**.  
You are free to use, modify, and distribute this project, provided that proper credit is given.

See the [LICENSE](LICENSE) file for full license details.

---

## 👤 Author

- **Tolga Demir**  
  Back-End Developer | Java | PostgreSQL | JDBC  
  [GitHub](https://github.com/tolgademir-co) · [LinkedIn](https://www.linkedin.com/in/tolgademir-co/)

---