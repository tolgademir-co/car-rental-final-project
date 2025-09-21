package com.tolgademir.model;

/**
 * Motorcycle entity -> motosiklet araç tipini temsil eder
 */
public class Motorcycle extends Vehicle {
    public Motorcycle(int id, String brand, String model, double value,
                      double priceHourly, double priceDaily, double priceWeekly, double priceMonthly) {
        super(id, "MOTORCYCLE", brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
    }

    @Override
    public double calculatePrice(String rentalType, int duration) {
        return switch (rentalType.toUpperCase()) {
            case "HOURLY" -> priceHourly * duration;
            case "DAILY" -> priceDaily * duration;
            case "WEEKLY" -> priceWeekly * duration;
            case "MONTHLY" -> priceMonthly * duration;
            default -> 0;
        };
    }
}