#include "../include/Car.h"

Car::Car(const std::string& license) : Vehicle(license) {}

VehicleType Car::getType() const {
    return VehicleType::CAR;
}