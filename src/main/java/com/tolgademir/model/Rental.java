package com.tolgademir.model;

import java.time.LocalDateTime;

/**
 * Rental entity -> rentals tablosunun karşılığıdır
 * // Kiralama bilgilerini tutar
 */
public class Rental {
    private int id;
    private int userId;
    private int vehicleId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double deposit;
    private String status; // ACTIVE | CANCELLED | COMPLETED

    public Rental(int id, int userId, int vehicleId,
                  LocalDateTime startDate, LocalDateTime endDate,
                  double deposit, String status) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.status = status;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public double getDeposit() { return deposit; }
    public void setDeposit(double deposit) { this.deposit = deposit; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", userId=" + userId +
                ", vehicleId=" + vehicleId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", deposit=" + deposit +
                ", status='" + status + '\'' +
                '}';
    }
}