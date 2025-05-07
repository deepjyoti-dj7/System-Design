#include "../include/ParkingLot.h"
#include <chrono>

ParkingLot* ParkingLot::instance = nullptr;

ParkingLot::ParkingLot() {}

ParkingLot* ParkingLot::getInstance() {
    if (instance == nullptr) {
        instance = new ParkingLot();
    }
    return instance;
}

void ParkingLot::setFeeStrategy(std::shared_ptr<ParkingFeeStrategy> strategy) {
    feeStrategy = strategy;
}

void ParkingLot::addFloor(std::shared_ptr<ParkingFloor> floor) {
    floors.push_back(floor);
}

std::shared_ptr<Ticket> ParkingLot::parkVehicle(Vehicle* vehicle) {
    for (auto& floor : floors) {
        ParkingSlot* slot = floor->getFreeSlot(vehicle);
        if (slot != nullptr) {
            slot->assignVehicle(vehicle);
            auto entryTime = std::chrono::system_clock::now();
            return std::make_shared<Ticket>(vehicle, slot, entryTime);
        }
    }
    return nullptr;
}

double ParkingLot::unparkVehicle(std::shared_ptr<Ticket> ticket) {
    auto exitTime = std::chrono::system_clock::now();
    double fee = feeStrategy->calculateFee(ticket->getVehicle(), ticket->getEntryTime(), exitTime);
    ticket->getSlot()->removeVehicle(ticket->getVehicle());
    return fee;
}
