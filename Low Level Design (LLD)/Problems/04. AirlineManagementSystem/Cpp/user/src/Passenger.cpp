#include "../include/Passenger.h"

Passenger::Passenger(const std::string& id, const std::string& name, 
                     const std::string& email, const std::string& phone) :
              User(id, name, email, phone, UserRole::PASSENGER) {}

void Passenger::addBooking(Booking* booking) {
    bookings.push_back(booking);
}

const std::vector<Booking*>& Passenger::getBookings() const {
    return bookings;
}

std::string Passenger::getEmail() const {
    return email;
}