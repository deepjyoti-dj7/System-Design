#pragma once
#include <string>
#include "../../utils/Enums.cpp"

class Seat {
private:
    std::string seatNumber;
    SeatClass seatClass;
    SeatStatus seatStatus;  

public:
    Seat(std::string seatNumber, SeatClass seatClass);
    
    void book();
    const std::string getSeatNumber() const;
    const SeatClass getSeatClass() const;
    const SeatStatus getSeatStatus() const;
};