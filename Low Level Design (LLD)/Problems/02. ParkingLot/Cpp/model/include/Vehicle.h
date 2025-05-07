#pragma once
#include <string>
#include "VehicleType.h"

class Vehicle {
private:    
    std::string licenseNumber;

public:
    Vehicle(const std::string& licenseNumber);
    
    virtual VehicleType getType() const = 0;
    std::string getLicenseNumber() const;
};