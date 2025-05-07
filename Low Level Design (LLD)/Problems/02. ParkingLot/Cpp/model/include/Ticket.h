#pragma once
#include "Vehicle.h"
#include "ParkingSlot.h"
#include "chrono"

class Ticket {
private:
    Vehicle* vehicle;
    ParkingSlot* parkingSlot;
    std::chrono::system_clock::time_point entryTime;

public:
    Ticket(Vehicle* vehicle, ParkingSlot* parkingSlot, std::chrono::system_clock::time_point entryTime);

    Vehicle* getVehicle() const;
    ParkingSlot* getSlot() const;
    std::chrono::system_clock::time_point getEntryTime() const;
};
