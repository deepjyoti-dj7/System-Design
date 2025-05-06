package model;

public class Truck extends Vehicle {
    public Truck(String license) {
        super(license);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.TRUCK;
    }
}