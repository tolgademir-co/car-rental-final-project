package com.tolgademir.service;

import com.tolgademir.dao.RentalDao;
import com.tolgademir.dao.UserDao;
import com.tolgademir.dao.VehicleDao;
import com.tolgademir.model.Rental;
import com.tolgademir.model.User;
import com.tolgademir.model.Vehicle;

import java.time.Duration;
import java.util.List;

/**
 * Service layer for rental operations
 * // Kiralama işlemleri için iş kurallarını yöneten katman
 */
public class RentalService {
    private final RentalDao rentalDao = new RentalDao();
    private final UserDao userDao = new UserDao();
    private final VehicleDao vehicleDao = new VehicleDao();

    /**
     * Create a new rental after applying business rules
     * // İş kurallarını kontrol ederek yeni kiralama oluşturur
     */
    public boolean createRental(Rental rental) {
        // 1. Tarih çakışması kontrolü
        boolean available = rentalDao.isVehicleAvailable(
                rental.getVehicleId(),
                rental.getStartDate(),
                rental.getEndDate()
        );
        if (!available) {
            System.out.println("❌ Vehicle is already rented in the selected time range.");
            return false;
        }

        // Kullanıcı ve araç bilgilerini DB’den al
        User user = userDao.findById(rental.getUserId());
        Vehicle vehicle = vehicleDao.findById(rental.getVehicleId());

        if (user == null || vehicle == null) {
            System.out.println("❌ User or vehicle not found.");
            return false;
        }

        // 2. Corporate hesaplar için min 1 ay kiralama kontrolü
        if ("CORPORATE".equalsIgnoreCase(user.getAccountType())) {
            long days = Duration.between(rental.getStartDate(), rental.getEndDate()).toDays();
            if (days < 30) {
                System.out.println("❌ Corporate accounts must rent for at least 30 days.");
                return false;
            }
        }

        // 3. Araç değeri > 2M TL kontrolü
        if (vehicle.getValue() > 2_000_000) {
            if (user.getAge() < 30) {
                System.out.println("❌ Must be at least 30 years old to rent vehicles over 2M value.");
                return false;
            }
            // %10 depozito hesapla
            double deposit = vehicle.getValue() * 0.10;
            rental.setDeposit(deposit);
        } else {
            rental.setDeposit(0);
        }

        // 4. Rental kaydını DB’ye yaz
        return rentalDao.insert(rental);
    }

    /**
     * Get all rentals for a given user
     * // Belirli bir kullanıcıya ait tüm kiralamaları getirir
     */
    public List<Rental> getRentalsByUser(int userId) {
        return rentalDao.findByUserId(userId);
    }

    /**
     * Cancel an active rental and refund deposit
     * // Aktif kiralamayı iptal eder, depozitoyu iade eder
     */
    public void cancelRental(int rentalId) {
        Rental rental = rentalDao.findById(rentalId);
        if (rental == null) {
            System.out.println("❌ Rental not found (ID=" + rentalId + ")");
            return;
        }

        boolean result = rentalDao.updateStatus(rentalId, "CANCELLED");
        if (result) {
            if (rental.getDeposit() > 0) {
                System.out.println("✅ Rental cancelled, deposit refunded: " + rental.getDeposit() + " TL");
            } else {
                System.out.println("✅ Rental cancelled (no deposit required).");
            }
        } else {
            System.out.println("❌ Failed to cancel rental (ID not found?)");
        }
    }

    /**
     * Complete a rental and handle deposit
     * // Kiralamayı tamamlar, depozitoyu iade eder
     */
    public void completeRental(int rentalId) {
        Rental rental = rentalDao.findById(rentalId);
        if (rental == null) {
            System.out.println("❌ Rental not found (ID=" + rentalId + ")");
            return;
        }

        boolean result = rentalDao.updateStatus(rentalId, "COMPLETED");
        if (result) {
            if (rental.getDeposit() > 0) {
                System.out.println("✅ Rental completed, deposit handled: " + rental.getDeposit() + " TL refunded.");
            } else {
                System.out.println("✅ Rental completed (no deposit required).");
            }
        } else {
            System.out.println("❌ Failed to complete rental (ID not found?)");
        }
    }
}