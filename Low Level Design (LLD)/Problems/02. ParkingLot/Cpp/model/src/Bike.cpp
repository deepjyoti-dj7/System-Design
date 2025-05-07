#include "../include/Bike.h"

Bike::Bike(const std::string& license) : Vehicle(license) {}

VehicleType Bike::getType() const {
    return VehicleType::BIKE;
}