#pragma once

#include "../../model/include/Vehicle.h"
#include "../../model/include/Car.h"
#include "../../model/include/Bike.h"
#include "../../model/include/Truck.h"
#include "../../model/include/VehicleType.h"

class VehicleFactory {
public:
    static Vehicle* createVehicle(VehicleType type, const std::string& license);
};
