package com.tolgademir.service;

import com.tolgademir.dao.RentalDao;
import com.tolgademir.dao.UserDao;
import com.tolgademir.dao.VehicleDao;
import com.tolgademir.model.Rental;
import com.tolgademir.model.RentalDetail;
import com.tolgademir.model.User;
import com.tolgademir.model.Vehicle;
import com.tolgademir.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
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

    // =========================
    // CREATE RENTAL (transaction)
    // =========================
    public boolean createRental(Rental rental) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            boolean available = rentalDao.isVehicleAvailable(
                    rental.getVehicleId(),
                    rental.getStartDate(),
                    rental.getEndDate(),
                    conn
            );
            if (!available) {
                System.out.println("❌ Vehicle is already rented in the selected time range.");
                return false;
            }

            User user = userDao.findById(rental.getUserId());
            Vehicle vehicle = vehicleDao.findById(rental.getVehicleId());

            if (user == null || vehicle == null) {
                System.out.println("❌ User or vehicle not found.");
                return false;
            }

            long days = Duration.between(rental.getStartDate(), rental.getEndDate()).toDays();
            if ("CORPORATE".equalsIgnoreCase(user.getAccountType()) && days < 30) {
                System.out.println("❌ Corporate accounts must rent for at least 30 days.");
                return false;
            }

            if (vehicle.getValue() > 2_000_000) {
                if (user.getAge() < 30) {
                    System.out.println("❌ Must be at least 30 years old to rent vehicles over 2M value.");
                    return false;
                }
                rental.setDeposit(vehicle.getValue() * 0.10);
            } else {
                rental.setDeposit(0);
            }

            long hours = Duration.between(rental.getStartDate(), rental.getEndDate()).toHours();
            String rentalType;
            int duration;

            if (hours < 24) {
                rentalType = "HOURLY";
                duration = (int) hours;
            } else if (days < 7) {
                rentalType = "DAILY";
                duration = (int) days;
            } else if (days < 30) {
                rentalType = "WEEKLY";
                duration = (int) (days / 7);
            } else {
                rentalType = "MONTHLY";
                duration = (int) (days / 30);
            }

            double price = vehicle.calculatePrice(rentalType, duration);
            rental.setPrice(price);

            System.out.println("✅ Rental price calculated: " + price + " TL (" + rentalType + ", " + duration + " unit)");

            boolean inserted = rentalDao.insert(conn, rental);

            conn.commit();
            return inserted;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Transaction failed, rollback applied.");
            return false;
        }
    }

    // =========================
    // CANCEL RENTAL (transaction)
    // =========================
    public void cancelRental(int rentalId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            Rental rental = rentalDao.findById(conn, rentalId);
            if (rental == null) {
                System.out.println("❌ Rental not found (ID=" + rentalId + ")");
                return;
            }

            boolean result = rentalDao.updateStatus(conn, rentalId, "CANCELLED");
            if (result) {
                conn.commit();
                if (rental.getDeposit() > 0) {
                    System.out.println("✅ Rental cancelled, deposit refunded: " + rental.getDeposit() + " TL");
                } else {
                    System.out.println("✅ Rental cancelled (no deposit required).");
                }
            } else {
                conn.rollback();
                System.out.println("❌ Failed to cancel rental (ID not found?)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // COMPLETE RENTAL (transaction)
    // =========================
    public void completeRental(int rentalId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            Rental rental = rentalDao.findById(conn, rentalId);
            if (rental == null) {
                System.out.println("❌ Rental not found (ID=" + rentalId + ")");
                return;
            }

            boolean result = rentalDao.updateStatus(conn, rentalId, "COMPLETED");
            if (result) {
                conn.commit();
                if (rental.getDeposit() > 0) {
                    System.out.println("✅ Rental completed, deposit handled: " + rental.getDeposit() + " TL refunded.");
                } else {
                    System.out.println("✅ Rental completed (no deposit required).");
                }
            } else {
                conn.rollback();
                System.out.println("❌ Failed to complete rental (ID not found?)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // JOIN destekli metodlar (RentalDetail döner)
    // =========================
    public RentalDetail getRentalDetailById(int rentalId) {
        try (Connection conn = DBConnection.getConnection()) {
            return rentalDao.findByIdWithDetails(conn, rentalId);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch rental detail: " + e.getMessage(), e);
        }
    }

    public List<RentalDetail> getRentalDetailsByUser(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            return rentalDao.findByUserIdWithDetails(conn, userId);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch user rentals with details: " + e.getMessage(), e);
        }
    }

    public List<RentalDetail> getAllRentalDetails() {
        try (Connection conn = DBConnection.getConnection()) {
            return rentalDao.findAllWithDetails(conn);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch all rentals with details: " + e.getMessage(), e);
        }
    }

    // =========================
    // BASIC READ METHODS (Rental döner)
    // =========================
    public List<Rental> getRentalsByUser(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            return rentalDao.findByUserId(conn, userId);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch rentals by user: " + e.getMessage(), e);
        }
    }

    public Rental getRentalById(int rentalId) {
        try (Connection conn = DBConnection.getConnection()) {
            return rentalDao.findById(conn, rentalId);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch rental by id: " + e.getMessage(), e);
        }
    }
}