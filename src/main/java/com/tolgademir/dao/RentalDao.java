package com.tolgademir.dao;

import com.tolgademir.model.Rental;
import com.tolgademir.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Rental
 * // rentals tablosu ile ilgili veritabanı işlemleri
 */
public class RentalDao {

    /**
     * Insert new rental
     * // Yeni kiralama ekler
     */
    public boolean insert(Rental rental) {
        String sql = "INSERT INTO rentals (user_id, vehicle_id, start_date, end_date, deposit, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rental.getUserId());
            stmt.setInt(2, rental.getVehicleId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getStartDate()));
            stmt.setTimestamp(4, Timestamp.valueOf(rental.getEndDate()));
            stmt.setDouble(5, rental.getDeposit());
            stmt.setString(6, rental.getStatus());

            stmt.executeUpdate();
            System.out.println("✅ Rental inserted into DB");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to insert rental: " + e.getMessage(), e);
        }
    }

    /**
     * Check vehicle availability in given date range
     * // Araç belirtilen tarihlerde müsait mi kontrol eder
     */
    public boolean isVehicleAvailable(int vehicleId, java.time.LocalDateTime start, java.time.LocalDateTime end) {
        String sql = "SELECT COUNT(*) FROM rentals WHERE vehicle_id = ? AND status = 'ACTIVE' AND (start_date < ? AND end_date > ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            stmt.setTimestamp(3, Timestamp.valueOf(start));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to check availability: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Find rental by ID
     * // ID ile kiralama bulur
     */
    public Rental findById(int rentalId) {
        String sql = "SELECT * FROM rentals WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to find rental: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Update rental status
     * // Kiralamanın durumunu günceller
     */
    public boolean updateStatus(int rentalId, String status) {
        String sql = "UPDATE rentals SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, rentalId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to update rental: " + e.getMessage(), e);
        }
    }

    /**
     * Find rentals by user ID
     * // Kullanıcıya ait tüm kiralamaları getirir
     */
    public List<Rental> findByUserId(int userId) {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to list rentals: " + e.getMessage(), e);
        }
        return rentals;
    }
}
