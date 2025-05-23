#include "../include/Seat.h"

Seat::Seat(std::string seatNumber, SeatClass seatClass)
    : seatNumber(std::move(seatNumber)), seatClass(seatClass), seatStatus(SeatStatus::AVAILABLE) { 
}

void Seat::book() {
    seatStatus = SeatStatus::BOOKED;
}

const std::string Seat::getSeatNumber() const {
    return seatNumber;
}

const SeatClass Seat::getSeatClass() const {
    return seatClass;
}

const SeatStatus Seat::getSeatStatus() const {
    return seatStatus;
}