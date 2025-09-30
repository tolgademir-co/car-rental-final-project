package com.tolgademir.dao;

import com.tolgademir.model.Car;
import com.tolgademir.model.Helicopter;
import com.tolgademir.model.Motorcycle;
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
                return mapToVehicle(rs);
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
                vehicles.add(mapToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to list vehicles: " + e.getMessage(), e);
        }
        return vehicles;
    }

    /**
     * ✅ List vehicles with pagination
     * // Araçları sayfalama ile listeler
     */
    public List<Vehicle> findAllPaged(int page, int pageSize) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY id LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vehicles.add(mapToVehicle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch vehicles with pagination: " + e.getMessage(), e);
        }
        return vehicles;
    }

    /**
     * Update vehicle
     * // Aracı günceller
     */
    public boolean update(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET type = ?, brand = ?, model = ?, value = ?, price_hourly = ?, price_daily = ?, price_weekly = ?, price_monthly = ? WHERE id = ?";
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
            stmt.setInt(9, vehicle.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to update vehicle: " + e.getMessage(), e);
        }
    }

    /**
     * Delete vehicle
     * // Aracı siler
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to delete vehicle: " + e.getMessage(), e);
        }
    }

    /**
     * Map ResultSet to correct Vehicle subclass
     * // DB’den gelen satırı doğru alt sınıfa dönüştürür
     */
    private Vehicle mapToVehicle(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        int id = rs.getInt("id");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        double value = rs.getDouble("value");
        double priceHourly = rs.getDouble("price_hourly");
        double priceDaily = rs.getDouble("price_daily");
        double priceWeekly = rs.getDouble("price_weekly");
        double priceMonthly = rs.getDouble("price_monthly");

        return switch (type.toUpperCase()) {
            case "CAR" -> new Car(id, brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
            case "MOTORCYCLE" -> new Motorcycle(id, brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
            case "HELICOPTER" -> new Helicopter(id, brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
            default -> throw new IllegalArgumentException("❌ Unknown vehicle type: " + type);
        };
    }
}