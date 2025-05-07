#pragma once
#include "../../model/include/Vehicle.h"
#include <chrono>

class ParkingFeeStrategy {
public: 
    virtual ~ParkingFeeStrategy() = default;

    virtual double calculateFee(Vehicle* vehicle, std::chrono::system_clock::time_point entry, 
        std::chrono::system_clock::time_point exit) const = 0;
};
