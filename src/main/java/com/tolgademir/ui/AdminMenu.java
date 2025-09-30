package com.tolgademir.ui;

import com.tolgademir.model.Car;
import com.tolgademir.model.Helicopter;
import com.tolgademir.model.Motorcycle;
import com.tolgademir.model.RentalDetail;
import com.tolgademir.model.User;
import com.tolgademir.model.Vehicle;
import com.tolgademir.service.RentalService;
import com.tolgademir.service.VehicleService;

import java.util.List;
import java.util.Scanner;

/**
 * Admin menu for vehicle and rental management
 * // Admin menüsü -> araç ekleme, listeleme (pagination), güncelleme, silme ve kiralamaları görme
 */
public class AdminMenu {
    private final User adminUser;
    private final VehicleService vehicleService = new VehicleService();
    private final RentalService rentalService = new RentalService();
    private final Scanner scanner = new Scanner(System.in);

    public AdminMenu(User adminUser) {
        this.adminUser = adminUser;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Add Vehicle");
            System.out.println("2. List Vehicles (Paged)");
            System.out.println("3. Update Vehicle");
            System.out.println("4. Delete Vehicle");
            System.out.println("5. List All Rentals (Detailed)");
            System.out.println("0. Logout");
            System.out.print("Select: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> listVehiclesPaged();
                case 3 -> updateVehicle();
                case 4 -> deleteVehicle();
                case 5 -> listAllRentals();
                case 0 -> {
                    System.out.println("👋 Logout successful.");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void addVehicle() {
        System.out.print("Type (CAR/MOTORCYCLE/HELICOPTER): ");
        String type = scanner.nextLine();
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Value: ");
        double value = scanner.nextDouble();
        System.out.print("Hourly Price: ");
        double priceHourly = scanner.nextDouble();
        System.out.print("Daily Price: ");
        double priceDaily = scanner.nextDouble();
        System.out.print("Weekly Price: ");
        double priceWeekly = scanner.nextDouble();
        System.out.print("Monthly Price: ");
        double priceMonthly = scanner.nextDouble();
        scanner.nextLine();

        Vehicle vehicle;
        switch (type.toUpperCase()) {
            case "CAR" -> vehicle = new Car(0, brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
            case "MOTORCYCLE" -> vehicle = new Motorcycle(0, brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
            case "HELICOPTER" -> vehicle = new Helicopter(0, brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
            default -> {
                System.out.println("❌ Invalid vehicle type.");
                return;
            }
        }

        if (vehicleService.addVehicle(vehicle)) {
            System.out.println("✅ Vehicle added successfully!");
        } else {
            System.out.println("❌ Failed to add vehicle.");
        }
    }

    /**
     * ✅ Vehicles with pagination
     */
    private void listVehiclesPaged() {
        int page = 1;
        int pageSize = 5; // her sayfada 5 araç gösterelim

        while (true) {
            System.out.println("\n=== VEHICLES (Page " + page + ") ===");
            List<Vehicle> vehicles = vehicleService.listVehiclesPaged(page, pageSize);

            if (vehicles.isEmpty()) {
                System.out.println("❌ No more vehicles to display.");
                break;
            }

            vehicles.forEach(System.out::println);

            System.out.print("\n(N)ext page | (P)revious page | (E)xit: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("N")) {
                page++;
            } else if (input.equals("P") && page > 1) {
                page--;
            } else if (input.equals("E")) {
                break;
            } else {
                System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void updateVehicle() {
        System.out.print("Enter vehicle ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Vehicle existing = vehicleService.getVehicleById(id);
        if (existing == null) {
            System.out.println("❌ Vehicle not found.");
            return;
        }

        System.out.print("New Brand: ");
        String brand = scanner.nextLine();
        System.out.print("New Model: ");
        String model = scanner.nextLine();
        System.out.print("New Value: ");
        double value = scanner.nextDouble();
        System.out.print("New Hourly Price: ");
        double priceHourly = scanner.nextDouble();
        System.out.print("New Daily Price: ");
        double priceDaily = scanner.nextDouble();
        System.out.print("New Weekly Price: ");
        double priceWeekly = scanner.nextDouble();
        System.out.print("New Monthly Price: ");
        double priceMonthly = scanner.nextDouble();
        scanner.nextLine();

        existing.setBrand(brand);
        existing.setModel(model);
        existing.setValue(value);
        existing.setPriceHourly(priceHourly);
        existing.setPriceDaily(priceDaily);
        existing.setPriceWeekly(priceWeekly);
        existing.setPriceMonthly(priceMonthly);

        if (vehicleService.updateVehicle(existing)) {
            System.out.println("✅ Vehicle updated successfully!");
        } else {
            System.out.println("❌ Failed to update vehicle.");
        }
    }

    private void deleteVehicle() {
        System.out.print("Enter vehicle ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (vehicleService.deleteVehicle(id)) {
            System.out.println("✅ Vehicle deleted successfully!");
        } else {
            System.out.println("❌ Failed to delete vehicle.");
        }
    }

    private void listAllRentals() {
        System.out.println("=== ALL RENTALS (DETAILED) ===");
        List<RentalDetail> rentals = rentalService.getAllRentalDetails();

        if (rentals.isEmpty()) {
            System.out.println("❌ No rentals found.");
        } else {
            for (RentalDetail d : rentals) {
                System.out.println("Rental #" + d.getRentalId() +
                        " | User: " + d.getUserName() +
                        " | Vehicle: " + d.getVehicleBrand() + " " + d.getVehicleModel() +
                        " | Start: " + d.getStartDate() +
                        " | End: " + d.getEndDate() +
                        " | Status: " + d.getStatus() +
                        " | Price: " + d.getPrice() + " TL");
            }
        }
    }
}