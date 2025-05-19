#include "../include/Seat.h"

Seat::Seat(std::string seatNumber, SeatClass seatClass, SeatStatus seatStatus) 
: seatNumber(seatNumber), seatClass(seatClass), seatStatus(seatStatus) {}

const std::string Seat::getSeatNumber() const {
    return seatNumber;
}

const SeatClass Seat::getSeatClass() const {
    return seatClass;
}

const SeatStatus Seat::getSeatStatus() const {
    return seatStatus;
}