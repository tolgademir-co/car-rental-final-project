package com.tolgademir.ui;

import com.tolgademir.model.Car;
import com.tolgademir.model.Helicopter;
import com.tolgademir.model.Motorcycle;
import com.tolgademir.model.User;
import com.tolgademir.model.Vehicle;
import com.tolgademir.service.VehicleService;

import java.util.List;
import java.util.Scanner;

/**
 * Admin menu for vehicle management
 * // Admin menüsü -> araç ekleme ve listeleme işlemleri
 */
public class AdminMenu {
    private final User adminUser;
    private final VehicleService vehicleService = new VehicleService();
    private final Scanner scanner = new Scanner(System.in);

    public AdminMenu(User adminUser) {
        this.adminUser = adminUser;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Add Vehicle");
            System.out.println("2. List Vehicles");
            System.out.println("0. Logout");
            System.out.print("Select: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> listVehicles();
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

        vehicleService.addVehicle(vehicle);
    }

    private void listVehicles() {
        List<Vehicle> vehicles = vehicleService.listVehicles();
        System.out.println("=== VEHICLES ===");
        vehicles.forEach(System.out::println);
    }
}