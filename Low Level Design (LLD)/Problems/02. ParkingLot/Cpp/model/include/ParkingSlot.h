#pragma once
#include "Vehicle.h"
#include "VehicleType.h"

class ParkingSlot {
private:
    int slotNumber;
    int floorNumber;
    VehicleType type;
    bool isOccupied;
    Vehicle* currentVehicle;

public: 
    ParkingSlot(int slotNumber, int floorNumber, VehicleType type);
    
    bool canFitVehicle(Vehicle* vehicle);
    void assignVehicle(Vehicle* vehicle);
    void removeVehicle(Vehicle* vehicle);
    int getSlotNumber() const;
    int getFloorNumber() const;
    Vehicle* getCurrentVehicle() const;
};