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

    // Create
    public boolean addVehicle(Vehicle vehicle) {
        return vehicleDao.insert(vehicle);
    }

    // Read - list all
    public List<Vehicle> listVehicles() {
        return vehicleDao.findAll();
    }

    // ✅ Read - list with pagination
    public List<Vehicle> listVehiclesPaged(int page, int pageSize) {
        return vehicleDao.findAllPaged(page, pageSize);
    }

    // Read - by ID
    public Vehicle getVehicleById(int id) {
        return vehicleDao.findById(id);
    }

    // Update
    public boolean updateVehicle(Vehicle vehicle) {
        return vehicleDao.update(vehicle);
    }

    // Delete
    public boolean deleteVehicle(int id) {
        return vehicleDao.delete(id);
    }
}