package factory;

import model.*;

public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType type, String license) {
        switch (type) {
            case CAR : return new Car(license);
            case BIKE : return new Bike(license);
            case TRUCK : return new Truck(license);
            default: return null;
        }
    }
}
