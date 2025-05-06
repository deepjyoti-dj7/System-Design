package model;

public class Bike extends Vehicle {
    public Bike(String license) {
        super(license);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.BIKE;
    }
}