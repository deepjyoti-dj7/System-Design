#pragma once

#include <string>
#include "../../flight/include/Flight.h"
#include "../../user/include/Passenger.h"
#include "../../booking/include/Seat.h"
#include "../../payment/include/Payment.h"
#include "../../utils/Enums.cpp"

class Booking {
private:
    std::string bookingId;
    Flight* flight;
    Passenger* passenger;
    Seat* seat;
    BookingStatus bookingStatus;
    Payment* payment;

public:
    Booking(const std::string& bookingId, Flight* flight, Passenger* passenger, Seat* seat);

    void confirmBooking(Payment* payment);

    std::string getBookingId() const;
};
