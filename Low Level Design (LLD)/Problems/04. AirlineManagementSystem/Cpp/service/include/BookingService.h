#pragma once

#include <string>
#include "../../flight/include/Flight.h"
#include "../../user/include/Passenger.h"
#include "../../booking/include/Booking.h"
#include "../../booking/include/Seat.h"
#include "../../payment/include/Payment.h"
#include "../../utils/Enums.cpp"
#include "NotificationService.h"

class BookingService {
public:
    Booking* createBooking(Flight* flight, Passenger* passenger, const std::string& seatNumber);
};
