package com.tolgademir.ui;

import com.tolgademir.model.RentalDetail;
import com.tolgademir.model.User;
import com.tolgademir.service.RentalService;
import com.tolgademir.service.VehicleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Customer menu for rental operations
 * // Customer menüsü -> araç listeleme, kiralama, iptal, tamamlama
 */
public class CustomerMenu {
    private final User customerUser;
    private final VehicleService vehicleService = new VehicleService();
    private final RentalService rentalService = new RentalService();
    private final Scanner scanner = new Scanner(System.in);

    public CustomerMenu(User customerUser) {
        this.customerUser = customerUser;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. List Vehicles");
            System.out.println("2. Rent Vehicle");
            System.out.println("3. My Rentals");
            System.out.println("4. Cancel Rental");
            System.out.println("5. Complete Rental");
            System.out.println("0. Logout");
            System.out.print("Select: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> listVehicles();
                case 2 -> rentVehicle();
                case 3 -> myRentals();
                case 4 -> cancelRental();
                case 5 -> completeRental();
                case 0 -> {
                    System.out.println("👋 Logout successful.");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void listVehicles() {
        System.out.println("=== VEHICLES ===");
        vehicleService.listVehicles().forEach(System.out::println);
    }

    private void rentVehicle() {
        System.out.print("Vehicle ID: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Start date (YYYY-MM-DDTHH:MM): ");
        LocalDateTime start = LocalDateTime.parse(scanner.nextLine());
        System.out.print("End date (YYYY-MM-DDTHH:MM): ");
        LocalDateTime end = LocalDateTime.parse(scanner.nextLine());

        // price ve deposit service tarafından hesaplanıyor
        com.tolgademir.model.Rental rental =
                new com.tolgademir.model.Rental(0, customerUser.getId(), vehicleId, start, end, 0, "ACTIVE", 0);
        rentalService.createRental(rental);
    }

    private void myRentals() {
        System.out.println("=== MY RENTALS (DETAILED) ===");
        List<RentalDetail> rentalDetails = rentalService.getRentalDetailsByUser(customerUser.getId());

        if (rentalDetails.isEmpty()) {
            System.out.println("❌ You have no rentals.");
        } else {
            for (RentalDetail d : rentalDetails) {
                System.out.println("Rental #" + d.getRentalId() +
                        " | Vehicle: " + d.getVehicleBrand() + " " + d.getVehicleModel() +
                        " | Start: " + d.getStartDate() +
                        " | End: " + d.getEndDate() +
                        " | Status: " + d.getStatus() +
                        " | Price: " + d.getPrice() + " TL");
            }
        }
    }

    private void cancelRental() {
        System.out.print("Rental ID: ");
        int rentalId = scanner.nextInt();
        scanner.nextLine();
        rentalService.cancelRental(rentalId);
    }

    private void completeRental() {
        System.out.print("Rental ID: ");
        int rentalId = scanner.nextInt();
        scanner.nextLine();
        rentalService.completeRental(rentalId);
    }
}