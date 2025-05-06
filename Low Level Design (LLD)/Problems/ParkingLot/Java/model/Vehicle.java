package model;

public abstract class Vehicle {
    private final String licenseNumber;

    public Vehicle(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public abstract VehicleType getType();
    public String getLicenseNumber() {
        return licenseNumber;
    }
}
