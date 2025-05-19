#include "../include/SeatFactory.h"

char seatClassToChar(SeatClass seatClass) {
    switch (seatClass) {
        case SeatClass::ECONOMY: return 'E';
        case SeatClass::BUSINESS: return 'B';
        case SeatClass::FIRST: return 'F';
        default: return 'U';
    }
}

std::vector<Seat> SeatFactory::generateSeats(const std::map<SeatClass, int>& seatConfig) {
    std::vector<Seat> seats;
    int counter = 1;

    for (const auto& entry : seatConfig) {
        SeatClass seatClass = entry.first;
        int count = entry.second;
        char prefix = seatClassToChar(seatClass);

        for (int i = 0; i < count; ++i) {
            std::string seatNumber = prefix + std::to_string(counter++);
            seats.emplace_back(seatNumber, seatClass);
        }
    }

    return seats;
}