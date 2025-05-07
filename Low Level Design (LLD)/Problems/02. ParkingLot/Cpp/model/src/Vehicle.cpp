#include "../include/Vehicle.h"

Vehicle::Vehicle(const std::string& licenseNumber) 
    : licenseNumber(licenseNumber) {}

std::string Vehicle::getLicenseNumber() const {
    return licenseNumber;
}