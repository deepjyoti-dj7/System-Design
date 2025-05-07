#include "./factory/include/VehicleFactory.h"
#include "./service/include/ParkingLot.h"
#include "./strategy/include/VehicleBasedHourlyFeeStrategy.h"
#include <iostream>
#include <memory>
#include <unordered_map>
#include <string>
#include <thread>
#include <chrono>

int main() {
    std::unordered_map<std::string, std::shared_ptr<Ticket>> activeTickets;
    auto lot = ParkingLot::getInstance();
    lot->setFeeStrategy(std::make_shared<VehicleBasedHourlyFeeStrategy>());

    auto floor1 = std::make_shared<ParkingFloor>(1);
    floor1->addSlot(new ParkingSlot(1, 1, VehicleType::CAR));
    floor1->addSlot(new ParkingSlot(2, 1, VehicleType::BIKE));
    floor1->addSlot(new ParkingSlot(3, 1, VehicleType::TRUCK));
    lot->addFloor(floor1);

    int choice;
    while (true) {
        std::cout << "\n--- Parking Lot Menu ---\n";
        std::cout << "1. Park Vehicle\n2. Unpark Vehicle\n3. Exit\nEnter choice: ";
        std::cin >> choice;
        std::cin.ignore();

        if (choice == 1) {
            std::string typeInput, license;
            std::cout << "Enter vehicle type (CAR/BIKE/TRUCK): ";
            std::getline(std::cin, typeInput);

            VehicleType type;
            if (typeInput == "CAR") type = VehicleType::CAR;
            else if (typeInput == "BIKE") type = VehicleType::BIKE;
            else if (typeInput == "TRUCK") type = VehicleType::TRUCK;
            else {
                std::cout << "Invalid vehicle type!\n";
                continue;
            }

            std::cout << "Enter license number: ";
            std::getline(std::cin, license);

            auto vehicle = VehicleFactory::createVehicle(type, license);
            auto ticket = lot->parkVehicle(vehicle);

            if (ticket) {
                activeTickets[license] = ticket;
                std::cout << "Your " << typeInput << " " << license
                          << " is parked at floor " << ticket->getSlot()->getFloorNumber()
                          << ", slot " << ticket->getSlot()->getSlotNumber() << "\n";
            } else {
                std::cout << "Parking full for " << typeInput << "\n";
            }
        } else if (choice == 2) {
            std::string lic;
            std::cout << "Enter license number to unpark: ";
            std::getline(std::cin, lic);

            auto it = activeTickets.find(lic);
            if (it != activeTickets.end()) {
                double fee = lot->unparkVehicle(it->second);
                activeTickets.erase(it);
                std::cout << "Your vehicle is unparked. \nFee: Rs." << fee << "\n";
            } else {
                std::cout << "No vehicle found with license: " << lic << "\n";
            }
        } else if (choice == 3) {
            std::cout << "Exiting... Thank you!\n";
            break;
        } else {
            std::cout << "Invalid option. Try again.\n";
        }
    }

    return 0;
}
