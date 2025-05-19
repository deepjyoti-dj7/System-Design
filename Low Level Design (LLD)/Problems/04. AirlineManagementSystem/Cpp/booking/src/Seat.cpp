#include "../include/Seat.h"

Seat::Seat(std::string seatNumber, SeatClass seatClass, SeatStatus seatStatus) 
: seatNumber(seatNumber), seatClass(seatClass), seatStatus(seatStatus) {}

std::string Seat::getSeatNumber() {
    return seatNumber;
}

SeatClass Seat::getSeatClass() {
    return seatClass;
}

SeatStatus Seat::getSeatStatus() {
    return seatStatus;
}