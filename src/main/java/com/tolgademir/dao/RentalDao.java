package com.tolgademir.dao;

import com.tolgademir.model.Rental;
import com.tolgademir.model.RentalDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Rental
 * // rentals tablosu ile ilgili veritabanı işlemleri
 */
public class RentalDao {

    /**
     * Insert new rental (uses external connection)
     * // Yeni kiralama ekler
     */
    public boolean insert(Connection conn, Rental rental) throws SQLException {
        String sql = "INSERT INTO rentals (user_id, vehicle_id, start_date, end_date, deposit, status, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rental.getUserId());
            stmt.setInt(2, rental.getVehicleId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getStartDate()));
            stmt.setTimestamp(4, Timestamp.valueOf(rental.getEndDate()));
            stmt.setDouble(5, rental.getDeposit());
            stmt.setString(6, rental.getStatus());
            stmt.setDouble(7, rental.getPrice());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Check vehicle availability
     * // Araç belirtilen tarihlerde müsait mi kontrol eder
     */
    public boolean isVehicleAvailable(int vehicleId, java.time.LocalDateTime start,
                                      java.time.LocalDateTime end, Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rentals " +
                "WHERE vehicle_id = ? AND status = 'ACTIVE' " +
                "AND (start_date < ? AND end_date > ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vehicleId);
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            stmt.setTimestamp(3, Timestamp.valueOf(start));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return false;
    }

    /**
     * Find rental by ID
     * // ID ile kiralama bulur
     */
    public Rental findById(Connection conn, int rentalId) throws SQLException {
        String sql = "SELECT * FROM rentals WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Rental(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("vehicle_id"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getDouble("deposit"),
                        rs.getString("status"),
                        rs.getDouble("price")
                );
            }
        }
        return null;
    }

    /**
     * Update rental status
     * // Kiralamanın durumunu günceller
     */
    public boolean updateStatus(Connection conn, int rentalId, String status) throws SQLException {
        String sql = "UPDATE rentals SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, rentalId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Find rentals by user ID
     * // Kullanıcıya ait tüm kiralamaları getirir
     */
    public List<Rental> findByUserId(Connection conn, int userId) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rentals.add(new Rental(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("vehicle_id"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getDouble("deposit"),
                        rs.getString("status"),
                        rs.getDouble("price")
                ));
            }
        }
        return rentals;
    }

    // ============================
    // ✅ JOIN ile detaylı sorgular
    // ============================

    public RentalDetail findByIdWithDetails(Connection conn, int rentalId) throws SQLException {
        String sql = """
                SELECT r.id AS rental_id,
                       u.name AS user_name,
                       v.brand AS vehicle_brand,
                       v.model AS vehicle_model,
                       r.start_date,
                       r.end_date,
                       r.status,
                       r.price
                FROM rentals r
                JOIN users u ON r.user_id = u.id
                JOIN vehicles v ON r.vehicle_id = v.id
                WHERE r.id = ?
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RentalDetail(
                        rs.getInt("rental_id"),
                        rs.getString("user_name"),
                        rs.getString("vehicle_brand"),
                        rs.getString("vehicle_model"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getDouble("price")
                );
            }
        }
        return null;
    }

    public List<RentalDetail> findByUserIdWithDetails(Connection conn, int userId) throws SQLException {
        List<RentalDetail> details = new ArrayList<>();
        String sql = """
                SELECT r.id AS rental_id,
                       u.name AS user_name,
                       v.brand AS vehicle_brand,
                       v.model AS vehicle_model,
                       r.start_date,
                       r.end_date,
                       r.status,
                       r.price
                FROM rentals r
                JOIN users u ON r.user_id = u.id
                JOIN vehicles v ON r.vehicle_id = v.id
                WHERE u.id = ?
                ORDER BY r.start_date DESC
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                details.add(new RentalDetail(
                        rs.getInt("rental_id"),
                        rs.getString("user_name"),
                        rs.getString("vehicle_brand"),
                        rs.getString("vehicle_model"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getDouble("price")
                ));
            }
        }
        return details;
    }

    public List<RentalDetail> findAllWithDetails(Connection conn) throws SQLException {
        List<RentalDetail> details = new ArrayList<>();
        String sql = """
                SELECT r.id AS rental_id,
                       u.name AS user_name,
                       v.brand AS vehicle_brand,
                       v.model AS vehicle_model,
                       r.start_date,
                       r.end_date,
                       r.status,
                       r.price
                FROM rentals r
                JOIN users u ON r.user_id = u.id
                JOIN vehicles v ON r.vehicle_id = v.id
                ORDER BY r.start_date DESC
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                details.add(new RentalDetail(
                        rs.getInt("rental_id"),
                        rs.getString("user_name"),
                        rs.getString("vehicle_brand"),
                        rs.getString("vehicle_model"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getDouble("price")
                ));
            }
        }
        return details;
    }
}