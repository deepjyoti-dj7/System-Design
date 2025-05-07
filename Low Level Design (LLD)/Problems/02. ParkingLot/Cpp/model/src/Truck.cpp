#include "../include/Truck.h"

Truck::Truck(const std::string& license) : Vehicle(license) {}

VehicleType Truck::getType() const {
    return VehicleType::TRUCK;
}