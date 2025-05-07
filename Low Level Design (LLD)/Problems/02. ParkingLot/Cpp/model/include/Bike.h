#pragma once
#include "Vehicle.h"

class Bike : public Vehicle {
public:
    Bike(const std::string& license);
    VehicleType getType() const override;
};