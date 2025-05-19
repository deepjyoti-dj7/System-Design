#pragma once
#include "User.h"
#include <vector>

class Booking;

class Passenger : public User {
private:
    std::vector<Booking> bookings;

public:
    Passenger(const std::string& id, const std::string& name, const std::string& email,
              const std::string& phone);

    void addBooking(const Booking& booking);
    const std::vector<Booking>& getBookings() const;
    std::string getEmail() const;
};