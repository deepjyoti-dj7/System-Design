#pragma once

#include <iostream>
#include <string>
#include "../../user/include/Passenger.h"
#include "../../booking/include/Booking.h"
#include "../../booking/include/Seat.h"
#include "../../flight/include/Flight.h"

class NotificationService {
public:
    void sendBookingConfirmation(Passenger* passenger, Booking* booking, Flight* flight, Seat* seat);
};
