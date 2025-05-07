#include "ParkingFeeStrategy.h"
#include "../../model/include/Vehicle.h"
#include "../../model/include/VehicleType.h"
#include <chrono>
#include <map>
#include <stdexcept>

class VehicleBasedHourlyFeeStrategy : public ParkingFeeStrategy {
private:
    std::map<VehicleType, double> hourlyRates;

public:
    VehicleBasedHourlyFeeStrategy();

    double calculateFee(Vehicle* vehicle, std::chrono::system_clock::time_point entry, 
        std::chrono::system_clock::time_point exit) const override;
};
