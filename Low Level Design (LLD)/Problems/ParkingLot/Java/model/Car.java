package model;

public class Car extends Vehicle {
    public Car(String license) {
        super(license);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }
}
