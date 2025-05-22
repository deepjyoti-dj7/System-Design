#pragma once
#include <vector>
#include <map>
#include <string>
#include "Seat.h"
#include "../../utils/Enums.cpp"

class SeatFactory {
public:
    static std::vector<Seat> generateSeats(const std::map<SeatClass, int>& seatConfig);
};