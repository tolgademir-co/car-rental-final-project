package com.tolgademir.model;

import java.time.LocalDateTime;

/**
 * RentalDetail DTO
 * JOIN ile kullanıcı + araç + kiralama bilgilerini göstermek için kullanılır
 */
public class RentalDetail {
    private int rentalId;
    private String userName;
    private String vehicleBrand;
    private String vehicleModel;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private double price;

    public RentalDetail(int rentalId, String userName, String vehicleBrand, String vehicleModel,
                        LocalDateTime startDate, LocalDateTime endDate,
                        String status, double price) {
        this.rentalId = rentalId;
        this.userName = userName;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
    }

    // --- Getters & Setters ---
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RentalDetail{" +
                "rentalId=" + rentalId +
                ", userName='" + userName + '\'' +
                ", vehicleBrand='" + vehicleBrand + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}