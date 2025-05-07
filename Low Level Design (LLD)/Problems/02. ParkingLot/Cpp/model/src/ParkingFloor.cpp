#include "../include/ParkingFloor.h"

ParkingFloor::ParkingFloor(int floorNumber) : floorNumber(floorNumber) {}

void ParkingFloor::addSlot(ParkingSlot* slot) {
    slots.push_back(slot);
}

ParkingSlot* ParkingFloor::getFreeSlot(Vehicle* vehicle) {
    for (auto& slot : slots) {
        if (slot->canFitVehicle(vehicle)) {
            return slot;
        }
    }
    return nullptr;
}

int ParkingFloor::getFloorNumber() const {
    return floorNumber;
}