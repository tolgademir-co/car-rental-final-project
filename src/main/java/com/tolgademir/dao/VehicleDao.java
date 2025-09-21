package com.tolgademir.dao;

import com.tolgademir.model.Vehicle;
import com.tolgademir.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Vehicle
 * // vehicles tablosu ile ilgili veritabanı işlemleri
 */
public class VehicleDao {

    /**
     * Insert new vehicle into DB
     * // Yeni araç ekler
     */
    public boolean insert(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (type, brand, model, value, price_hourly, price_daily, price_weekly, price_monthly) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getType());
            stmt.setString(2, vehicle.getBrand());
            stmt.setString(3, vehicle.getModel());
            stmt.setDouble(4, vehicle.getValue());
            stmt.setDouble(5, vehicle.getPriceHourly());
            stmt.setDouble(6, vehicle.getPriceDaily());
            stmt.setDouble(7, vehicle.getPriceWeekly());
            stmt.setDouble(8, vehicle.getPriceMonthly());

            stmt.executeUpdate();
            System.out.println("✅ Vehicle inserted: " + vehicle.getBrand() + " " + vehicle.getModel());
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to insert vehicle: " + e.getMessage(), e);
        }
    }

    /**
     * Find vehicle by ID
     * // ID ile araç bulur
     */
    public Vehicle findById(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getDouble("value"),
                        rs.getDouble("price_hourly"),
                        rs.getDouble("price_daily"),
                        rs.getDouble("price_weekly"),
                        rs.getDouble("price_monthly")
                ) {
                    @Override
                    public double calculatePrice(String rentalType, int duration) {
                        return 0; // sadece DB için
                    }
                };
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to find vehicle: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * List all vehicles
     * // Tüm araçları listeler
     */
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getDouble("value"),
                        rs.getDouble("price_hourly"),
                        rs.getDouble("price_daily"),
                        rs.getDouble("price_weekly"),
                        rs.getDouble("price_monthly")
                ) {
                    @Override
                    public double calculatePrice(String rentalType, int duration) {
                        return 0;
                    }
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to list vehicles: " + e.getMessage(), e);
        }
        return vehicles;
    }
}
