package com.tolgademir.model;

/**
 * Car entity -> otomobil araç tipini temsil eder
 */
public class Car extends Vehicle {
    public Car(int id, String brand, String model, double value,
               double priceHourly, double priceDaily, double priceWeekly, double priceMonthly) {
        super(id, "CAR", brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
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