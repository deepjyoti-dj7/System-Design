#include "../include/Ticket.h"

Ticket::Ticket(Vehicle* vehicle, ParkingSlot* parkingSlot, std::chrono::system_clock::time_point entryTime) 
    : vehicle(vehicle), parkingSlot(parkingSlot), entryTime(std::chrono::system_clock::now()) {}

Vehicle* Ticket::getVehicle() const {
    return vehicle;
}

ParkingSlot* Ticket::getSlot() const {
    return parkingSlot;
}

std::chrono::system_clock::time_point Ticket::getEntryTime() const {
    return entryTime;
}
