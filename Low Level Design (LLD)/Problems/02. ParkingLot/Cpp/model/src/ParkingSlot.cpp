#include "../include/ParkingSlot.h"

ParkingSlot::ParkingSlot(int slotNumber, int floorNumber, VehicleType type) 
    : slotNumber(slotNumber), floorNumber(floorNumber), type(type),
    isOccupied(false), currentVehicle(nullptr) {}

bool ParkingSlot::canFitVehicle(Vehicle* vehicle) {
    return !isOccupied && vehicle->getType() == type;
}

void ParkingSlot::assignVehicle(Vehicle* vehicle) {
    currentVehicle = vehicle;
    isOccupied = true;
}

void ParkingSlot::removeVehicle(Vehicle* vehicle) {
    currentVehicle = nullptr;
    isOccupied = false;
}

int ParkingSlot::getSlotNumber() const {
    return slotNumber;
}

int ParkingSlot::getFloorNumber() const {
    return floorNumber;
}

Vehicle* ParkingSlot::getCurrentVehicle() const {
    return currentVehicle;
}