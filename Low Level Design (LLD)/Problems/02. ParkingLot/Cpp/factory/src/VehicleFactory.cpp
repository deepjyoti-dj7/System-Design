#include "../include/VehicleFactory.h"

Vehicle* VehicleFactory::createVehicle(VehicleType type, const std::string& license) {
    switch (type) {
        case VehicleType::CAR:
            return new Car(license); 
        case VehicleType::BIKE:
            return new Bike(license);
        case VehicleType::TRUCK:
            return new Truck(license);
        default:
            return nullptr; 
    }
}
