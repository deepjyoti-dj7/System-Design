#pragma once
#include "Vehicle.h"
#include "ParkingSlot.h"
#include <vector>

class ParkingFloor {
private:  
    int floorNumber;
    std::vector<ParkingSlot*> slots;

public:
    ParkingFloor(int floorNumber);

    void addSlot(ParkingSlot* slot);
    ParkingSlot* getFreeSlot(Vehicle* vehicle);
    int getFloorNumber() const;
};