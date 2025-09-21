package com.tolgademir.model;

/**
 * Helicopter entity -> helikopter araç tipini temsil eder
 */
public class Helicopter extends Vehicle {
    public Helicopter(int id, String brand, String model, double value,
                      double priceHourly, double priceDaily, double priceWeekly, double priceMonthly) {
        super(id, "HELICOPTER", brand, model, value, priceHourly, priceDaily, priceWeekly, priceMonthly);
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