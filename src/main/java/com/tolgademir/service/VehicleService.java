package com.tolgademir.service;

import com.tolgademir.dao.VehicleDao;
import com.tolgademir.model.Vehicle;

import java.util.List;

/**
 * Service layer for vehicle operations
 * // Araç işlemleri için iş kurallarını yöneten katman
 */
public class VehicleService {
    private final VehicleDao vehicleDao = new VehicleDao();

    /**
     * Add new vehicle
     */
    public boolean addVehicle(Vehicle vehicle) {
        return vehicleDao.insert(vehicle);
    }

    /**
     * List all vehicles
     */
    public List<Vehicle> listVehicles() {
        return vehicleDao.findAll();
    }

    /**
     * List vehicles with pagination
     */
    public List<Vehicle> listVehiclesPaged(int page, int pageSize) {
        return vehicleDao.findAllPaged(page, pageSize);
    }

    /**
     * Get vehicle by ID
     */
    public Vehicle getVehicleById(int id) {
        return vehicleDao.findById(id);
    }

    /**
     * Update vehicle
     */
    public boolean updateVehicle(Vehicle vehicle) {
        return vehicleDao.update(vehicle);
    }

    /**
     * Delete vehicle
     */
    public boolean deleteVehicle(int id) {
        return vehicleDao.delete(id);
    }

    // ==========================
    // 🔍 FILTER METHODS
    // ==========================

    /**
     * Filter vehicles by type
     */
    public List<Vehicle> filterByType(String type) {
        return vehicleDao.findByType(type);
    }

    /**
     * Filter vehicles by brand
     */
    public List<Vehicle> filterByBrand(String brand) {
        return vehicleDao.findByBrand(brand);
    }

    /**
     * Filter vehicles by price range
     */
    public List<Vehicle> filterByPriceRange(double min, double max) {
        return vehicleDao.findByPriceRange(min, max);
    }
}