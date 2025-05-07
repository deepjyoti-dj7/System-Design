#pragma once
#include "../../model/include/ParkingFloor.h"
#include "../../model/include/Ticket.h"
#include "../../strategy/include/ParkingFeeStrategy.h"
#include <vector>
#include <memory>
#include <chrono>

class ParkingLot {
private:
    static ParkingLot* instance;
    std::vector<std::shared_ptr<ParkingFloor>> floors;
    std::shared_ptr<ParkingFeeStrategy> feeStrategy;

    ParkingLot();

public:
    static ParkingLot* getInstance();

    void setFeeStrategy(std::shared_ptr<ParkingFeeStrategy> strategy);
    void addFloor(std::shared_ptr<ParkingFloor> floor);

    std::shared_ptr<Ticket> parkVehicle(Vehicle* vehicle);
    double unparkVehicle(std::shared_ptr<Ticket> ticket);
};