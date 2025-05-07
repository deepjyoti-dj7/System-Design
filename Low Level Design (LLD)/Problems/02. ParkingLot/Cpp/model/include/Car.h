#pragma once
#include "Vehicle.h"

class Car : public Vehicle {
public:
    Car(const std::string& license);
    VehicleType getType() const override;
};