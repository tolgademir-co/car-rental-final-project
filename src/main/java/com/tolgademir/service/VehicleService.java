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
     * Add a new vehicle
     * // Yeni araç ekler
     */
    public boolean addVehicle(Vehicle vehicle) {
        return vehicleDao.insert(vehicle);
    }

    /**
     * List all vehicles
     * // Tüm araçları listeler
     */
    public List<Vehicle> listVehicles() {
        return vehicleDao.findAll();
    }

    /**
     * Find vehicle by ID
     * // ID ile araç bulur
     */
    public Vehicle getVehicleById(int id) {
        return vehicleDao.findById(id);
    }

    /**
     * Update vehicle
     * // Aracı günceller
     */
    public boolean updateVehicle(Vehicle vehicle) {
        return vehicleDao.update(vehicle);
    }

    /**
     * Delete vehicle
     * // Aracı siler
     */
    public boolean deleteVehicle(int id) {
        return vehicleDao.delete(id);
    }
}