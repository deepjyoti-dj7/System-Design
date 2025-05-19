#pragma once
#include <string>
#include "../../utils/Enums.cpp"

class Seat {
private:
    std::string seatNumber;
    SeatClass seatClass;
    SeatStatus seatStatus;  

public:
    Seat(std::string seatNumber, SeatClass seatClass, SeatStatus seatStatus);
    
    void book();
    std::string getSeatNumber();
    SeatClass getSeatClass();
    SeatStatus getSeatStatus();
};