#pragma once
#include "Vehicle.h"

class Truck : public Vehicle {
public:
    Truck(const std::string& license);
    VehicleType getType() const override;
};