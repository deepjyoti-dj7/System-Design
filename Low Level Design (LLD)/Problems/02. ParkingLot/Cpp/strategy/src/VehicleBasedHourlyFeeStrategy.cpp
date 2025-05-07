#include "../include/VehicleBasedHourlyFeeStrategy.h"

VehicleBasedHourlyFeeStrategy::VehicleBasedHourlyFeeStrategy() {
    hourlyRates[VehicleType::BIKE] = 20.0;
    hourlyRates[VehicleType::CAR] = 40.0;
    hourlyRates[VehicleType::TRUCK] = 70.0;
}

double VehicleBasedHourlyFeeStrategy::calculateFee(Vehicle* vehicle, 
    std::chrono::system_clock::time_point entry, 
    std::chrono::system_clock::time_point exit) const {

    auto duration = std::chrono::duration_cast<std::chrono::hours>(exit - entry).count();
    long long hours = std::max(1LL, duration); 

    VehicleType type = vehicle->getType();
    if (hourlyRates.find(type) == hourlyRates.end()) {
    throw std::invalid_argument("No hourly rate defined for vehicle type.");
    }

    double rate = hourlyRates.at(type);
    return hours * rate;
}