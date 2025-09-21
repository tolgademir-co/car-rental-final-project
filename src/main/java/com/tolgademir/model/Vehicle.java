package com.tolgademir.model;

/**
 * Abstract Vehicle entity -> vehicles tablosunun karşılığıdır
 * // Araç tipleri bu sınıftan türetilir
 */
public abstract class Vehicle {
    protected int id;
    protected String type;
    protected String brand;
    protected String model;
    protected double value;
    protected double priceHourly;
    protected double priceDaily;
    protected double priceWeekly;
    protected double priceMonthly;

    public Vehicle(int id, String type, String brand, String model,
                   double value, double priceHourly, double priceDaily,
                   double priceWeekly, double priceMonthly) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.value = value;
        this.priceHourly = priceHourly;
        this.priceDaily = priceDaily;
        this.priceWeekly = priceWeekly;
        this.priceMonthly = priceMonthly;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public double getPriceHourly() { return priceHourly; }
    public void setPriceHourly(double priceHourly) { this.priceHourly = priceHourly; }

    public double getPriceDaily() { return priceDaily; }
    public void setPriceDaily(double priceDaily) { this.priceDaily = priceDaily; }

    public double getPriceWeekly() { return priceWeekly; }
    public void setPriceWeekly(double priceWeekly) { this.priceWeekly = priceWeekly; }

    public double getPriceMonthly() { return priceMonthly; }
    public void setPriceMonthly(double priceMonthly) { this.priceMonthly = priceMonthly; }

    // --- Abstract method ---
    public abstract double calculatePrice(String rentalType, int duration);

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", value=" + value +
                '}';
    }
}